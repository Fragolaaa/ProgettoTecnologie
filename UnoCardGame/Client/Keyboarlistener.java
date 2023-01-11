package client;

import java.util.Scanner;
//utilizzare per nickname
public class Keyboarlistener extends Thread{
    
    Scanner s;
    public Keyboarlistener()
    {
        s = new Scanner(System.in);
    }


    @Override
    public void run() {
        shared inst = shared.getInstance();

        while(!inst.toClose)
        {
            String line = s.nextLine();
            if(inst.getSocket()==null)
                continue;

            inst.getOut().println(line);
        }
        
    }

}