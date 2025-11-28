import java.util.Scanner;

public class Lab7
{
    public static void main(String[] args)
    {
        int i, j;
        int a[] = new int[20];
        int buck_rem = 0, buck_cap = 4, rate = 3;
        int sent, recv;

        System.out.println("Clock\tPacketSize\tAccepted\tSent\tRemaining");
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the number of packets:");
        int n = in.nextInt();

        System.out.println("Enter the packets:");
        for(i = 1; i <= n; i++)
            a[i] = in.nextInt();

        for(i = 1; i <= n; i++)
        {
            // RECEIVING
            if(a[i] != 0)
            {
                if(buck_rem + a[i] > buck_cap)
                {
                    recv = -1;   // dropped
                }
                else
                {
                    recv = a[i]; // accepted
                    buck_rem += a[i];
                }
            }
            else
                recv = 0;

            // SENDING
            if(buck_rem != 0)
            {
                try { Thread.sleep(1000); } catch(Exception e) {}

                if(buck_rem < rate)
                {
                    sent = buck_rem;
                    buck_rem = 0;
                }
                else
                {
                    sent = rate;
                    buck_rem = buck_rem - rate;
                }
            }
            else
                sent = 0;

            // OUTPUT
            if(recv == -1)
                System.out.println(i + "\t" + a[i] + "\tDropped\t\t" + sent + "\t" + buck_rem);
            else
                System.out.println(i + "\t" + a[i] + "\t" + recv + "\t\t" + sent + "\t" + buck_rem);
        }

        in.close();
    }
}

