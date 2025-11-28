import java.util.*;

public class ford {
    static final int INF = 999;
    int[] dist;
    int n;

    ford(int n) {
        this.n = n;
        dist = new int[n + 1];
    }

    void bellman(int src, int[][] g) {
        Arrays.fill(dist, INF);
        dist[src] = 0;

        for (int i = 1; i < n; i++)
            for (int u = 1; u <= n; u++)
                for (int v = 1; v <= n; v++)
                    if (g[u][v] != INF && dist[u] + g[u][v] < dist[v])
                        dist[v] = dist[u] + g[u][v];

        for (int u = 1; u <= n; u++)
            for (int v = 1; v <= n; v++)
                if (g[u][v] != INF && dist[u] + g[u][v] < dist[v])
                    System.out.println("The Graph contains negative edge cycle");

        for (int i = 1; i <= n; i++)
            System.out.println("distance of source " + src + " to " + i + " is " + dist[i]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of vertices:");
        int n = sc.nextInt();

        int[][] g = new int[n + 1][n + 1];
        System.out.println("Enter adjacency matrix:");

        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++) {
                g[i][j] = sc.nextInt();
                if (i == j) g[i][j] = 0;
                else if (g[i][j] == 0) g[i][j] = INF;
            }

        System.out.println("Enter source vertex:");
        int src = sc.nextInt();

        new ford(n).bellman(src, g);
        sc.close();
    }
}

