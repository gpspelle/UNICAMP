#include <stdio.h>

long long int mat[1010][1010];
long long int convol(long long int mat[1010][1010], int i, int j, int n, int m) {

    int a, b; 

    long long int result = 0;
    for(a = i -  m/2; a <= i + m/2; a++) {
        if(a < 0 || a >= n)
            continue;
        for(b = j - m/2; b <= j + m/2; b++) {
            if(b < 0 || b >= n)
                continue;
            result += mat[a][b];
        }
    }  
    return result;
}

int main(void) {

    int n, m;

	int print = 0;
    while (scanf("%d %d", &n, &m) == 2) {

        int i, j;

	if(print)
		printf("\n");
	print = 1;
        for(i = 0; i < n; i++)
            for(j = 0; j < n; j++)
                scanf(" %lld", &mat[i][j]);

        /*for(i = 0; i < n; i++) {
            for(j = 0; j < n; j++)
                printf(" %lld ", mat[i][j]);
            printf("\n");
        }
        printf("\n");*/

        for(i = 0; i < n; i++) {
            for(j = 1; j < n; j++) {
                mat[i][j] += mat[i][j-1];
            }
        }

        /*for(i = 0; i < n; i++) {
            for(j = 0; j < n; j++)
                printf(" %lld ", mat[i][j]);
            printf("\n");
        }
        printf("\n");
*/
        for(i = 0; i < n; i++) {
            for(j = 1; j < n; j++) {
                mat[j][i] += mat[j-1][i];
            }
        }
        
  /*      for(i = 0; i < n; i++) {
            for(j = 0; j < n; j++)
                printf(" %lld ", mat[i][j]);
            printf("\n");
        }
        printf("\n");
*/
        long long int total = 0;
        for(i = 0; i < n-m+1; i++) {
            for(j = 0; j < n - m + 1; j++) {
                long long int aux = mat[i+m-1][j+m-1];

                if(j - 1 >= 0) {
                    aux -= mat[i+m-1][j-1];
                } 
                
                if(i - 1 >= 0) {
                    aux -= mat[i-1][j+m-1];
                }
               
                if(i - 1 >= 0 && j - 1 >= 0) {
                    aux += mat[i-1][j-1];
                }
      
                printf("%lld\n", aux);
                total += aux;
            }
        }
        printf("%lld\n", total);
        
    }


    return 0;
}
