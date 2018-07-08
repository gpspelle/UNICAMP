#include <stdio.h>

int main(void) {

    int n;
    long long int out = 0;
    scanf(" %d", &n);

    int mult = 1; 

    int j;

    while(1) {
        int k = 0;
        for(j = mult*mult; j <= n; j+=mult) {
            k += 1;
        }

        out += k;
        mult+=1;
        if (k == 0) {
            break;
        }
    }

    printf("%lld\n", out);


    return 0;
}
