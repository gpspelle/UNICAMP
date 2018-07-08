#include <stdio.h>

int main(void) {

    int T;
    scanf(" %d", &T);

    while(T--) {
        int R, C, i, j;
        char matrix[510][510] = {0};

        scanf(" %d %d", &R, &C);
        for(i = 0; i < R; i++) {
            for(j = 0; j < C; j++) {
                scanf(" %c", &matrix[i][j]);
            }
        }

        int move = 1;
        while(move) {
            move = 0;
            for(i = 0; i < R; i++) {
                for(j = 0; j < C; j++) {
                    /* ITS A FUCKING CIVILIZATION */
                    if(matrix[i][j] >= 97 && matrix[i][j] <= 122) {
                        if(i > 0 && matrix[i-1][j] == '.') {
                            matrix[i-1][j] = matrix[i][j] - 32;
                            move = 1;
                        } 
                        else if(i > 0 && (matrix[i-1][j] >= 65 && matrix[i-1][j] <= 90) && matrix[i-1][j] != matrix[i][j] - 32) {
                            matrix[i-1][j] = '*';
                        }
                        if(j > 0 && matrix[i][j-1] == '.') {
                            matrix[i][j-1] = matrix[i][j] - 32;
                            move = 1;
                        }
                        else if(j > 0 && (matrix[i][j-1] >= 65 && matrix[i][j-1] <= 90) && matrix[i][j-1] != matrix[i][j] - 32) {
                            matrix[i][j-1] = '*';
                        }
                        if(i < R-1 && matrix[i+1][j] == '.') {
                            matrix[i+1][j] = matrix[i][j] - 32;
                            move = 1;
                        }
                        else if(i < R-1 && (matrix[i+1][j] >= 65 && matrix[i+1][j] <= 90) && matrix[i+1][j] != matrix[i][j] - 32) {
                            matrix[i+1][j] = '*';
                        }
                        if(j < C-1 && matrix[i][j+1] == '.') {
                            matrix[i][j+1] = matrix[i][j] - 32;
                            move = 1;
                        }
                        else if(j < C-1 && (matrix[i][j+1] >= 65 && matrix[i][j+1] <= 90) && matrix[i][j+1] != matrix[i][j] - 32) {
                            matrix[i][j+1] = '*';
                        }
                    }
                }
            }
            for(i = 0; i < R; i++) {
                for(j = 0; j < C; j++) {
                    if(matrix[i][j] >= 65 && matrix[i][j] <= 90) {
                        matrix[i][j] += 32;
                    }
                }
            }
        }
        for(i = 0; i < R; i++) {
            for(j = 0; j < C; j++) {
                printf("%c", matrix[i][j]);
            }
            printf("\n");
        }
        printf("\n");
    }
    return 0;
}
