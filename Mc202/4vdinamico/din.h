#ifndef DIN_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef char comando[12]; 
typedef struct {
	int tam, ocup;
	int f, l;
	int passadoF, passadoL;

}Tinfo;
void shift(Tinfo *info, char ***vetor);
void print_first(Tinfo *info, char ***vetor);
void push(Tinfo *info, char ***vetor);
void pop(Tinfo *info, char ***vetor);
void print_last(Tinfo *info, char ***vetor);
void is_empty(Tinfo *info, char ***vetor);
void unshift(Tinfo *info, char ***vetor);
void opcao(Tinfo *info, char ***vetor);

#endif
