public class Restaurante {
    private static final int NUM_CADEIRAS = 5;
    private static final int MAX_CLIENTES_ESPERANDO = 20;
    private static int clientesSentados = 0;
    private static int clientesEsperando = 0;

    public static void main(String[] args) {
        //criando 100 instâncias de clientes como pedido pelo monitor
        for (int i = 0; i < 100; i++) {
                Cliente cliente = new Cliente(i);
                cliente.start();
        }
    }

    public static void sentarCliente(int id) throws InterruptedException {
        boolean sentou = false;
        while (!sentou) {
            synchronized (Restaurante.class) {
                if (clientesSentados < NUM_CADEIRAS) {
                    //cliente senta em uma cadeira
                    clientesSentados++;
                    System.out.println("Cliente " + id + " sentou na cadeira. Clientes sentados: " + clientesSentados);
                    sentou = true;
                } else if (clientesEsperando < MAX_CLIENTES_ESPERANDO) {
                    //cliente entra na fila se ainda houver espaço
                    clientesEsperando++;
                    System.out.println("Cliente " + id + " entrou na fila de espera. Clientes esperando: " + clientesEsperando);
                    sentou = true; //evitando q entre em loop infinito
                }
            }

            //se o cliente ainda nao conseguiu sentar ou entrar na fila, aguarda 2s e tenta de novo
            if (!sentou) {
                Thread.sleep(2000);
            }
        }
    }

    public static void liberarCadeiras() {
        synchronized (Restaurante.class) {
            clientesSentados--;
            System.out.println("Cliente terminou de jantar e saiu. Clientes sentados: " + clientesSentados);

            //liberando lugares pra proxima rodada quando os 5 já tiverem saído
            if (clientesSentados == 0 && clientesEsperando > 0) {
                System.out.println("Liberando lugares para clientes na fila de espera.");
                int liberar = Math.min(NUM_CADEIRAS, clientesEsperando);
                clientesSentados = liberar;
                clientesEsperando -= liberar;
                System.out.println(liberar + " clientes da fila sentaram. Clientes esperando: " + clientesEsperando);
            }
        }
    }
}
