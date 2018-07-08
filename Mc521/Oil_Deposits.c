#include <stdio.h>

void find(char matrix[102][102], int j, int k, int n, int m) {
        if(j > 0 && k > 0 && matrix[j-1][k-1] == '@') {
            matrix[j-1][k-1] = '_';
            find(matrix, j-1, k-1, n, m);
        }
        if(j > 0 && matrix[j-1][k] == '@') {
            matrix[j-1][k] = '_';
            find(matrix, j-1, k, n, m);
        }
        if(j > 0 && k < m-1 && matrix[j-1][k+1] == '@') {
            matrix[j-1][k+1] = '_';
            find(matrix, j-1, k+1, n, m);
        }
        if(k > 0 && matrix[j][k-1] == '@') {
            matrix[j][k-1] = '_';
            find(matrix, j, k-1, n, m);
        }
        if(k < m-1 && matrix[j][k+1] == '@') {
            matrix[j][k+1] = '_';
            find(matrix, j, k+1, n, m);
        }
        if(j < n-1  && k > 0 && matrix[j+1][k-1] == '@') {
            matrix[j+1][k-1] = '_';
            find(matrix, j+1, k-1, n, m);
        }
        if(j < n-1 && matrix[j+1][k] == '@') {
            matrix[j+1][k] = '_';
            find(matrix, j+1, k, n, m);
        }
        if(j < n-1 && k < m-1 && matrix[j+1][k+1] == '@') {
            matrix[j+1][k+1] = '_';
            find(matrix, j+1, k+1, n, m);
        }

}
int main(void) {

    int m, n, i ,j, k;

    scanf(" %d %d", &m, &n);

    int aka= 0;
    char matrix[102][102];
    while(m != 0) {
        
        int diff = 0;
        for(i = 0; i < m; i++) {
            scanf(" %[^\n]", matrix[i]);
        }

        for(j = 0; j < m; j++) {
            for(k = 0; k < n; k++) {
                if(matrix[j][k] == '@') {
                    matrix[j][k] = '_';
                    find(matrix, j, k, m, n);
                    diff+=1;
                }
            }
        } 
        printf("%d\n", diff);
        scanf(" %d %d", &m, &n);
    }

    return 0;
}
