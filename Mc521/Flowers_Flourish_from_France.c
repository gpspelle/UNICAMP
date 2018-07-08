#include <stdio.h>
#include <string.h>
#include <ctype.h>

int main(void) {
    while(1) {
        char b[50][100];
        char c;
        int count1 = 0, count2 = 0, i;

        c = getchar();
        if(c == '*') {
            break;
        } else {
            b[count1][count2++] = c;
        }

        c = getchar();
        while(c != '\n') {
           if(c == ' ') {
                count1++;
                count2 = 0;
                c = getchar();
           } 
           b[count1][count2++] = c; 
           c = getchar();
           
        }
        for(i = 0; i <= count1; i++) {
            b[i][0] = tolower(b[i][0]);
        }

        int tauto = 1;
        char l = b[0][0];
        for(i = 0; i <= count1 && tauto; i++) {
            if(l == b[i][0]) {
                tauto = 1;
            } else {
                tauto = 0;
            }
        }

        if(tauto == 0) {
            printf("N\n");
        } else {
            printf("Y\n");
        }
    }
    return 0;
}
