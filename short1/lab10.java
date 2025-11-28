import java.util.*;

public class lab10 {
    public static void main(String[] a) {
        Scanner in = new Scanner(System.in);

        int[] pk = new int[20];
        int buck = 0, cap = 4, rate = 3;

        System.out.println("Clock\tPacketSize\tAccepted\tSent\tRemaining");

        System.out.println("Enter the number of packets:");
        int n = in.nextInt();

        System.out.println("Enter the packets:");
        for (int i = 1; i <= n; i++) pk[i] = in.nextInt();

        for (int i = 1; i <= n; i++) {

            int recv = (pk[i] != 0 && buck + pk[i] <= cap) ? pk[i] : (pk[i] == 0 ? 0 : -1);
            if (recv != -1) buck += recv;

            int sent = 0;
            if (buck > 0) {
                try { Thread.sleep(1000); } catch (Exception e) {}
                sent = Math.min(buck, rate);
                buck -= sent;
            }

            if (recv == -1)
                System.out.println(i + "\t" + pk[i] + "\tDropped\t\t" + sent + "\t" + buck);
            else
                System.out.println(i + "\t" + pk[i] + "\t" + recv + "\t\t" + sent + "\t" + buck);
        }

        in.close();
    }
}

