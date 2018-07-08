#include <stdio.h>
#include <math.h>
int main(void) {

    long long int N, i;
    scanf(" %lld", &N);

    if(N < 4) {
        printf("N");
        return 0;
    }
    
    int flag = 0;
    for(i = 2; i < sqrt(N) + 1; ++i) {
        if(N % i == 0) {
            flag = 1;
            break;
        } 
    }
   
    if(flag) {
        printf("S");
    } else {
        printf("N");
    }
    
    return 0;
}
