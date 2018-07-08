#include <stdio.h>

long long int pd[100010];

long long int rec(int t) {
    if(t == 0) {
        return 0;
    } else if(t == 1) {
        return 1;
    } else if(t % 2 == 0) {
        t = t / 2; 
        if(pd[t] > -1) {
            return pd[t];
        } else {
            return rec(t);
        }
    } else {
        t = t / 2;
        if(pd[t] > -1 && pd[t+1] > -1) {
            return pd[t] + pd[t+1];
        } else if(pd[t] == -1 && pd[t+1] > -1) {
            return rec(t) + pd[t+1];
        } else if(pd[t] > -1 && pd[t+1] == -1) {
            return pd[t] + rec(t+1);
        } else {
            return rec(t) + rec(t+1);
        } 
    }

}

int main(void) {

    int t, i;

    scanf(" %d", &t);

    for(i = 0; i < 100010; i++) {
        pd[i] = -1;
    }

    for(i = 0; i <= 99999; i++) {
        pd[i] = rec(i);
    }

    while(t != 0) {
        
        long long int max = 0;
        for(i = 0; i <= t; i++) {
            if(pd[i] > max) {
                max = pd[i];
            }
        }
        printf("%lld\n", max);
        scanf(" %d", &t);
    }

    return 0;
}
