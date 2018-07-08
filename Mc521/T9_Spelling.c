#include <stdio.h>
#include <string.h>

int main(void) {

    int T, i;

    scanf(" %d", &T);

    char buffer[1010];
    int caso = 1;
    
    fgets(buffer, 1000, stdin);
    while (T--) {

        /*scanf("%[^\ns", buffer);*/
        fgets(buffer, 1000, stdin);
        int tam = strlen(buffer);
        printf("Case #%d: ", caso);
        caso++;
        int state = -1;
        for(i = 0; i < tam; i++) {
            switch(buffer[i]) {
                case 'a': if(state == 2) { printf(" "); } state = 2; printf("2"); break;
                case 'b': if(state == 2) { printf(" "); } state = 2; printf("22");break;
                case 'c': if(state == 2) { printf(" "); } state = 2; printf("222");break;
                case 'd': if(state == 3) { printf(" "); } state = 3; printf("3");break;
                case 'e': if(state == 3) { printf(" "); } state = 3; printf("33");break;
                case 'f': if(state == 3) { printf(" "); } state = 3; printf("333");break;
                case 'g': if(state == 4) { printf(" "); } state = 4; printf("4");break;
                case 'h': if(state == 4) { printf(" "); } state = 4; printf("44");break;
                case 'i': if(state == 4) { printf(" "); } state = 4; printf("444");break;
                case 'j': if(state == 5) { printf(" "); } state = 5; printf("5");break;
                case 'k': if(state == 5) { printf(" "); } state = 5; printf("55");break;
                case 'l': if(state == 5) { printf(" "); } state = 5; printf("555");break;
                case 'm': if(state == 6) { printf(" "); } state = 6; printf("6");break;
                case 'n': if(state == 6) { printf(" "); } state = 6; printf("66");break;
                case 'o': if(state == 6) { printf(" "); } state = 6; printf("666");break;
                case 'p': if(state == 7) { printf(" "); } state = 7; printf("7");break;
                case 'q': if(state == 7) { printf(" "); } state = 7; printf("77");break;
                case 'r': if(state == 7) { printf(" "); } state = 7; printf("777");break;
                case 's': if(state == 7) { printf(" "); } state = 7; printf("7777");break;
                case 't': if(state == 8) { printf(" "); } state = 8; printf("8");break;
                case 'u': if(state == 8) { printf(" "); } state = 8; printf("88");break;
                case 'v': if(state == 8) { printf(" "); } state = 8; printf("888");break;
                case 'w': if(state == 9) { printf(" "); } state = 9; printf("9");break;
                case 'x': if(state == 9) { printf(" "); } state = 9; printf("99");break;
                case 'y': if(state == 9) { printf(" "); } state = 9; printf("999");break;
                case 'z': if(state == 9) { printf(" "); } state = 9; printf("9999");break;
                case ' ': if(state == 10) { printf(" "); } state = 10; printf("0"); break;
            }
        }
        printf("\n");

    }
    return 0;
}
