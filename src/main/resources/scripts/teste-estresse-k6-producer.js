import http from 'k6/http';
import { check, sleep } from 'k6';

// Configuração do teste de carga
export const options = {
    stages: [
        { duration: '10s', target: 20 },  // Rampa de subida: vai de 0 a 20 usuários virtuais em 10s
        { duration: '30s', target: 100 }, // Carga alta: mantém 100 usuários virtuais simultâneos por 30s
        { duration: '10s', target: 0 },   // Rampa de descida: desacelera até 0 em 10s
    ],
    thresholds: {
        http_req_failed: ['rate<0.01'],   // Falhas de HTTP devem ser menores que 1%
        http_req_duration: ['p(95)<200'], // 95% das requisições devem responder em menos de 200ms
    },
};

export default function () {
    const url = 'http://host.docker.internal:8080/api/v1/pedidos';

    // Geramos IDs de clientes de 1 a 50 para distribuir pelas 3 partições do Kafka
    const clienteId = `CLI-${Math.floor(Math.random() * 50) + 1}`;

    const payload = JSON.stringify({
        clienteId: clienteId,
        itens: [
            {
                produto: "Teclado Mecânico",
                quantidade: Math.floor(Math.random() * 5) + 1,
                precoUnitario: 299.90
            },
            {
                produto: "Mouse Gamer",
                quantidade: Math.floor(Math.random() * 2) + 1,
                precoUnitario: 149.90
            }
        ]
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const res = http.post(url, payload, params);

    // Validação do status da resposta HTTP
    check(res, {
        'status é 200 ou 201': (r) => r.status === 200 || r.status === 201,
    });

    // Pequena pausa entre requisições de cada VU para simular fluxo contínuo
    sleep(0.05);
}