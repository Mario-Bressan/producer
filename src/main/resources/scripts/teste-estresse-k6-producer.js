
import http from 'k6/http';
import { check, sleep } from 'k6';
import { Counter } from 'k6/metrics';

const pedidosCriados = new Counter('pedidos_criados');
const pedidosFalharam = new Counter('pedidos_falharam');

const BASE_URL = __ENV.BASE_URL || 'http://127.0.0.1:8080';

export const options = {
    stages: [
        { duration: '10s', target: 10 },
        { duration: '20s', target: 30 },
        { duration: '10s', target: 0 },
    ],
    thresholds: {
        http_req_failed: ['rate<0.01'],
        http_req_duration: ['p(95)<1000'],
    },
};

export default function () {
    const clienteId = `CLI-${Math.floor(Math.random() * 50) + 1}`;

    const payload = JSON.stringify({
        clienteId,
        itens: [
            {
                produto: 'Teclado Mecânico',
                quantidade: Math.floor(Math.random() * 5) + 1,
                precoUnitario: 299.90,
            },
            {
                produto: 'Mouse Gamer',
                quantidade: Math.floor(Math.random() * 2) + 1,
                precoUnitario: 149.90,
            },
        ],
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
        timeout: '10s',
    };

    const res = http.post(
        `${BASE_URL}/api/v1/pedidos`,
        payload,
        params
    );

    const sucesso = check(res, {
        'pedido criado (201)': (r) => r.status === 201,
    });

    if (sucesso) {
        pedidosCriados.add(1);
    } else {
        pedidosFalharam.add(1);
        console.error(
            `Falha HTTP ${res.status}: ${res.error || res.body}`
        );
    }

    sleep(0.1);
}
