#include <stdio.h>

char matrix[10][100005];
int mov[50005][2];

int N;
int found = 0;
int press = 0;
int save_pos = 0;
int count = 0;
int mov_counter = 0;
void find(int x, int y) {

    while(x != N) {
        /*IREMOS NO TETO*/
        if(y == 0) {
           /*ANDEI PARA A DIREITA*/
           if(matrix[y][x+1] == '0') {
                if(press == 0) {
                    press = 1;
                    save_pos = x;
                    count = 1;
                } else {
                    count++;
                }
                x+=1;
           } else {
                if(press == 1) {
                    mov[mov_counter][0] = save_pos;
                    mov[mov_counter++][1] = count;
                    press = 0;
                }
                x+=1;
                y+=1;
           }
        } else if(y == 9) {
           /*ANDEI PARA A DIREITA*/
           if(matrix[y][x+1] == '0') {
               x+=1;
           } else {
                press = 1;
                save_pos = x;
                count = 1;
                x+=1;
                y-=1;
           }
        } else {
            /*ESTAMOS NO AR*/
            if(matrix[y-1][x+1] == '0') {
                /*IREMOS SUBINDO*/
                if(press == 1) {
                    x+=1;
                    y-=1;
                    count++;
                } else {
                    press = 1;
                    save_pos = x;
                    count = 1;
                    x+=1;
                    y-=1;
                }
            } else {
                /*IREMOS CAINDO*/
                if(press == 1) {
                    mov[mov_counter][0] = save_pos;
                    mov[mov_counter++][1] = count;
                    press = 0;
                    x+=1;
                    y+=1;
                } else {
                    x+=1;
                    y+=1;
                }

            }
    
        }
    }

    if(press == 1) {
        mov[mov_counter][0] = save_pos;
        mov[mov_counter++][1] = count;
    }
}
int dfs(int x, int y) {

    if (matrix[y][x] == 'X') {
        return 0;
    }

    if(x == N) {
        matrix[y][x] = '0';
        return 1;
    }

    int aux = 0;
    if(y == 0) {
        /*ANDANDO PARA A DIREITA E PARA BAIXO*/
        aux = dfs(x+1, y+1);
        if (aux == 0) {
            /*ANDANDO PARA A DIREITA*/
            aux = dfs(x+1, y);

            if(aux == 1) {
                matrix[y][x] = '0';
                return 1;
            } else {
                matrix[y][x] = 'X';
            }
        } else {
            matrix[y][x] = '0';
            return 1;
        }
    } else if(y == 9) {
        /*ANDANDO PARA A DIREITA*/
        aux = dfs(x+1, y);
        if (aux == 0) {
            /*ANDANDO PARA A DIREITA E PARA CIMA*/
            aux = dfs(x+1, y-1);

            if(aux == 1) {
                matrix[y][x] = '0';
                return 1;
            } else {
                matrix[y][x] = 'X';
            }
        } else {
            matrix[y][x] = '0';
            return 1;
        }
       
       
    } else {
        /*ANDANDO PARA A DIREITA E PARA BAIXO*/
        if(y < 9) {
            aux = dfs(x+1, y+1);        
        }
            
        if(aux == 0) {
            /*ANDANDO PARA A DIREITA E PARA CIMA*/
            if(y > 0)
                aux = dfs(x+1, y-1);

            if(aux == 1) {
                matrix[y][x] = '0';
                return 1;
            } else {
                matrix[y][x] = 'X';
            }
        } else {
            matrix[y][x] = '0';
            return 1;
        }
    }

    return 0;
}
int main(void) {

    int i;
    scanf(" %d", &N);

    for(i = 0; i < 10; i++) {
        scanf("%s", matrix[i]);
    }
    
    dfs(0, 9);

    /*for(i = 0; i < 10; i++) {
        printf("[%s]\n", matrix[i]);
    }*/

    find(0, 9); 

    printf("%d\n", mov_counter);
    for(i = 0; i < mov_counter; i++) {
        printf("%d %d\n", mov[i][0], mov[i][1]); 
    } 
    return 0;
}
