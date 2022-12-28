class ClientThread extends Thread {
    private Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            // Crea uno stream di input dal socket
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            // Crea uno stream di output verso il socket
            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true
            );

            // Ricevi i dati dal client
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // Elabora i dati ricevuti dal client
                // ...

                // Invia una risposta al client
                out.println(response);
            }
            // Chiudi il socket
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
}