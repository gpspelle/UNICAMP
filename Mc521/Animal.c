#include <stdio.h>
#include <string.h>

int main(void) {

    char p1[20], p2[20], p3[20]; 
    scanf(" %s %s %s", p1, p2, p3);

    /* Se eh um vertebrado*/
    if(!strcmp(p1, "vertebrado")) {
        /* Se eh uma ave */
        if(!strcmp(p2, "ave")) {
            /* Se eh carnivoro */
            if(!strcmp(p3, "carnivoro")) {
                printf("aguia\n"); 
            } 
            /* Se eh onivoro */
            else {
                printf("pomba\n");
            }
        }
        /* Se eh um mamifero*/
        else {
            /* Se eh um onivoro  */
            if(!strcmp(p3, "onivoro")) {
                printf("homem\n");
            }
            else {
                printf("vaca\n");
            } 
        }
    }
    /* Se eh um invertebrado */
    else {
        /* Se eh uma ave */
        if(!strcmp(p2, "inseto")) {
            /* Se eh carnivoro */
            if(!strcmp(p3, "hematofago")) {
                printf("pulga\n"); 
            } 
            /* Se eh onivoro */
            else {
                printf("lagarta\n");
            }
        }
        /* Se eh um mamifero*/
        else {
            /* Se eh um onivoro  */
            if(!strcmp(p3, "hematofago")) {
                printf("sanguessuga\n");
            }
            else {
                printf("minhoca\n");
            } 
        }

    }
    return 0;
}
