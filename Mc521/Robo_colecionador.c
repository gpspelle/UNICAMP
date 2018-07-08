#include <stdio.h>

char map[102][102];
char acoes[50005];
long int figurinhas = 0;
int N, M, S;
void busca(int x, int y, char ori, int inst) {


 /*   printf("Busca em (x, y, ori, inst): (%d %d %c %d)\n", x, y, ori, inst);*/
    if(map[x][y] == '*') {
        map[x][y] = '.';
        figurinhas += 1;
    }

    if (inst == S)
        return;

    if(ori == 'N') {
        if (x > 0 && acoes[inst] == 'F') {
            if (map[x-1][y] == '#') {
                busca(x, y, ori, inst+1);
            } else {
                busca(x-1, y, ori, inst+1);
            }
        } else if( acoes[inst] == 'D' ) {
            busca(x, y, 'L', inst+1);
        } else if( acoes[inst] == 'E') {
            busca(x, y, 'O', inst+1);
        } else {
            busca(x, y, ori, inst+1);
        }
    } else if(ori == 'S') {
        if (x < N - 1 && acoes[inst] == 'F') {
            if (map[x+1][y] == '#') {
                busca(x, y, ori, inst+1);
            } else {
                busca(x+1, y, ori, inst+1);
            }
        } else if( acoes[inst] == 'D' ) {
            busca(x, y, 'O', inst+1);
        } else if( acoes[inst] == 'E') {
            busca(x, y, 'L', inst+1);
        } else {
            busca(x, y, ori, inst+1);
        }

    } else if(ori == 'L') {
        if (y < M-1 && acoes[inst] == 'F') {
            if(map[x][y+1] == '#') {
                busca(x, y, ori, inst+1);
            } else {
                busca(x, y+1, ori, inst+1);
            }
        } else if( acoes[inst] == 'D' ) {
            busca(x, y, 'S', inst+1);
        } else if( acoes[inst] == 'E') {
            busca(x, y, 'N', inst+1);
        } else {
            busca(x, y, ori, inst+1);
        }

    } else if(ori == 'O') {
        if (y > 0 && acoes[inst] == 'F') {
            if(map[x][y-1] == '#') {
                busca(x, y, ori, inst+1);
            } else {
                busca(x, y-1, ori, inst+1);
            }
        } else if( acoes[inst] == 'D' ) {
            busca(x, y, 'N', inst+1);
        } else if( acoes[inst] == 'E') {
            busca(x, y, 'S', inst+1);
        } else {
            busca(x, y, ori, inst+1);
        }
    }
}
int main(void) {


    while(1) {
        scanf(" %d %d %d", &N, &M, &S);


        if(N == 0 && N == M && S == N)
            break;

        int i, j;
        for(i = 0; i < N; i++){
            scanf(" %s", map[i]);
        }
        for(i = 0; i < S; i++) {
            scanf(" %c", &acoes[i]); 
        }

        /*for(i = 0; i < N; i++) {
            printf("[%s]\n", map[i]);
        }

        for(i = 0; i < S; i++) {
            printf("[%c] - ", acoes[i]);
        }
        printf("\n");*/
        for(i = 0; i < N; i++) {
            for(j = 0; j < M; j++) {
               if(map[i][j] == 'N') {
                    busca(i, j, 'N', 0);
                    break;
               } else if(map[i][j] == 'S') {
                    busca(i, j, 'S', 0);
                    break;
               } else if(map[i][j] == 'L') {
                    busca(i, j, 'L', 0);
                    break;
               } else if(map[i][j] == 'O') {
                    busca(i, j, 'O', 0);
                    break;
               }
            }
        }

        printf("%ld\n", figurinhas);
        figurinhas = 0;

    }
    return 0;
}
