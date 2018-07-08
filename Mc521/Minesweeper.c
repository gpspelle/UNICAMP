#include <stdio.h>
#include <string.h>

int get_total(char matrix[100][100], int j, int k, int n, int m) {

    int total = 0;
    if(j > 0 && k > 0 && matrix[j-1][k-1] > 10) {
        total++; 
    }
    if(j > 0 && matrix[j-1][k] > 10) {
        total++; 
    }
    if(j > 0 && k < m-1 && matrix[j-1][k+1] > 10) {
        total++; 
    }
    if(k > 0 && matrix[j][k-1] > 10) {
        total++; 
    }
    if(k < m-1 && matrix[j][k+1] > 10) {
        total++; 
    }
    if(j < n-1  && k > 0 && matrix[j+1][k-1] > 10) {
        total++; 
    }
    if(j < n-1 && matrix[j+1][k] > 10) {
        total++; 
    }
    if(j < n-1 && k < m-1 && matrix[j+1][k+1] > 10) {
        total++; 
    }
    return total;
}
int main(void) {

    int T, i;
    char matrix[100][100];

    scanf(" %d", &T);

    for(i = 0; i < T; i++) {
        int n, m, j, k, blah = 0, found = 0;  
        scanf(" %d %d", &n, &m);
      
        for(j = 0; j < n; j++) {
            scanf(" %[^\n]s", matrix[j]);
        }
    
        for(j = 0; j < n; j++) {
            for(k = 0; k < m; k++) {
                matrix[j][k] -= 48;
            }
        }
     
        for(j = 0; j < n && !blah; j++) {
            for(k = 0; k < m && !blah; k++) {
                /* Caso tenha um F na posicao */
                if(matrix[j][k] > 10) {
                    continue;
                }

                found = 1;
                if(get_total(matrix, j, k, n, m) != matrix[j][k]) {
                    blah = 1;
                }
            }
        }
        
        if(!blah && found) {
            printf("Well done Clark!\n");
        } else {
            printf("Please sweep the mine again!\n");
        }
    }

    return 0;
}
