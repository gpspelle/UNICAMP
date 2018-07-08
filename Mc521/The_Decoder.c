#include <stdio.h>
#include <string.h>

int main(void) {

    char buffer[1200000];
    while(scanf(" %[^\n]s", buffer) != EOF) {
        
        int i;
        for(i = 0; i < strlen(buffer); i++) {
            buffer[i] -= 7;
        }

        printf("%s\n", buffer);
    }

    return 0;
}
