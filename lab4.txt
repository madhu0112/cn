import java.util.*;

public class CRC1 {

    static void crc(int[] data, int[] gen) {
        for (int i = 0; i <= data.length - gen.length; i++)
            if (data[i] == 1)
                for (int j = 0; j < gen.length; j++)
                    data[i + j] ^= gen[j];
    }

    static int[] arr(String s, int pad) {
        int[] a = new int[s.length() + pad];
        for (int i = 0; i < s.length(); i++) a[i] = s.charAt(i) - '0';
        return a;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ---- Generate CRC ----
        System.out.print("Enter message bits: ");
        String msg = sc.nextLine();
        System.out.print("Enter generator: ");
        String genStr = sc.nextLine();

        int[] gen = arr(genStr, 0);
        int[] data = arr(msg, gen.length - 1);
        crc(data, gen);

        System.out.print("The checksum code is: ");
        int[] code = arr(msg, gen.length - 1);
        for (int i = 0; i < gen.length - 1; i++)
            code[msg.length() + i] = data[msg.length() + i];
        for (int x : code) System.out.print(x);
        System.out.println();

        // ---- Check CRC ----
        System.out.print("Enter checksum code: ");
        String recv = sc.nextLine();
        System.out.print("Enter generator: ");
        genStr = sc.nextLine();

        gen = arr(genStr, 0);
        data = arr(recv, gen.length - 1);
        crc(data, gen);

        boolean ok = true;
        for (int x : data) if (x == 1) ok = false;

        System.out.println(ok ? "Data stream is valid" : "Data stream is invalid. CRC error occured.");
    }
}

