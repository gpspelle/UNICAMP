#include <stdio.h>

char map[102][102];

int N, M, K;

int busca(int x, int y) {

    int res = 1;

    if(x > 0 && map[x-1][y] == '#') {
        return 0;
    }
    if(x < N && map[x+1][y] == '#') {
        return 0;
    }
    if(y > 0 && map[x][y-1] == '#') {
        return 0;
    }
    if(y < M && map[x][y+1] == '#') {
        return 0;
    }

    if(x > 0 && map[x-1][y] == '@') {
        map[x][y] = '.';
        res &= busca(x-1, y);
    }
    if(x < N && map[x+1][y] == '@') {
        map[x][y] = '.';
        res &= busca(x+1, y);
    }
    if(y > 0 && map[x][y-1] == '@') {
        map[x][y] = '.';
        res &= busca(x, y-1);
    }
    if(y < M && map[x][y+1] == '@') {
        map[x][y] = '.';
        res &= busca(x, y+1);
    }

    map[x][y] = '.';
    return res;
}
int main(void) {
    int i, j;
    int x, y;
    scanf(" %d %d", &N, &M);

    for(i = 0; i < N; i++) {
        scanf(" %s", map[i]);
    }

    scanf(" %d", &K);
    
    for(i = 0; i < K; i++) {
        scanf(" %d %d", &x, &y);
        x-=1;
        y-=1;
        if(map[x][y] == '#') {
            map[x][y] = '@';
        }
    }

    /*for(i =0;i<N;i++) {
        for(j=0;j<M;j++)
            printf("%c", map[i][j]);
        printf("\n");
    } */  
    int num = 0;
    for(i = 0; i < N; i++) {
        for(j = 0; j < M; j++) {
            if(map[i][j] == '@') {
                num += busca(i, j);
            }
        }
    }

    /*for(i =0;i<N;i++) {
        for(j=0;j<M;j++)
            printf("%c", map[i][j]);
        printf("\n");
    } */  
    printf("%d\n", num);
    return 0;
}
