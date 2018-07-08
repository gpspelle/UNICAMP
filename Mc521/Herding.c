#include <stdio.h>

int t = 0;
int aux[1000][1000];
int values[1000000];
int global_n;
int global_m;
void explore(char map[1000][1000], int i, int j) {

    if (aux[i][j] < t) {
        if(map[i][j] == 'S') {
            values[aux[i][j]] = 0;
            aux[i][j] = t;
            values[t] = 1;
            if(i + 1 < global_n)
                explore(map, i+1, j);
        } else if (map[i][j] == 'N') {
            values[aux[i][j]] = 0;
            aux[i][j] = t;
            values[t] = 1;
            if(i > 0)
            explore(map, i-1, j);
        } else if(map[i][j] == 'E') {
            values[aux[i][j]] = 0;
            aux[i][j] = t;
            values[t] = 1;
            if(j < global_m)
                explore(map, i, j+1);
        } else if (map[i][j] == 'W') {
            values[aux[i][j]] = 0;
            aux[i][j] = t;
            values[t] = 1;
            if(j > 0)
                explore(map, i, j-1);
        }
    }
}
int main(void) {

    char map[1000][1000];

    int n, m, i, j;
    scanf(" %d %d", &n, &m);

    for(i = 0; i < n; i++) {
        scanf(" %s", map[i]); 
        for(j = 0; j < m; j++) {
            aux[i][j] = 0;
            values[i*n + j] = 0;
        }
    } 
    global_n = n;
    global_m = m;

    for(i = 0; i < n; i++) {
        for(j = 0; j < m; j++) {
            if(aux[i][j] == 0) {
                t += 1;
                explore(map, i, j);
            }
        }
    }

    /*for(i = 0; i < n; i++) {
        for(j = 0; j < m; j++) {
            printf("%d ", aux[i][j]);
        }
        printf("\n");
    }*/
    int ans = 0;

    for(i = 0; i <= t; i++) {
        if(values[i] != 0) {
            ans++;
        }
    }
    printf("%d\n", ans);
}
