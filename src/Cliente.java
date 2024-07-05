public class Cliente extends Thread {
    private int id;

    public Cliente(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Restaurante.sentarCliente(id);

            // Simula o cliente jantando
            Thread.sleep(1000); //cliente jantando por 1s

            //cliente termina de jantar e vai embora
            Restaurante.liberarCadeiras();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //criando 100 instancias de clientes
        for (int i = 0; i < 100; i++) {
            Cliente cliente = new Cliente(i);
            cliente.start();
        }
    }
}
