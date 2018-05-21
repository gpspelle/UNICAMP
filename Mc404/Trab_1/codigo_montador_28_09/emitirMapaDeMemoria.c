/* Gabriel Pellegrino da Silva / 172358 */

#include "montador.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <locale.h>

/* Estrutura de lista utilizada para armazenar DefRotulo e Simbolos */
typedef struct lista {
    struct lista *prox;
    char *palavra;
    char *valor; 
    int endereco;
    int posicao;
} lista;

/* Pragmas das funcoes utilizadas */
void convert_dec_hex();
void change_pos();
void insere_linha();
void insere_instrucao();
int insere_diretiva();
int insere_endereco();
int insere_lista();
void insere_lista_simbolo();
void endereco_vazio();
lista * percorre_lista();
void libera_memoria();
void insere_digitos();
void preenche_endereco();
int verifica_matriz();
void put_0_right();

/* Argumentos:
 * matriz: matriz de caracteres onde esta armazenado o mapa.
 * linha: inteiro com a linha atual.
 * posicao: inteiro com a posicao atual. 0: esquerda, 1: direita.
 */ 
void put_0_right(char **matriz, int linha, int posicao) {
    if(posicao) {
        matriz[linha][10] = ' ';
        matriz[linha][11] = '0';
        matriz[linha][12] = '0';
        matriz[linha][13] = ' ';
        matriz[linha][14] = '0';
        matriz[linha][15] = '0';
        matriz[linha][16] = '0';
    }
}

/* Argumentos:
 * matriz: matriz de caracteres onde esta armazenado o mapa.
 * linha: inteiro com a linha atual.
 * posicao: inteiro com a posicao atual. 0: esquerda, 1: direita.
 *
 * Retorno:
 * 1: se a linha ja esta preenchida.
 * 0: se a linha ainda nao esta preenchida.
 * */
int verifica_matriz(char **matriz, int linha, int posicao) {

    if(posicao == 0) {
        if(isalpha(matriz[linha][4]) || isdigit(matriz[linha][4])) {
            return 1;
        }
    } else {
        if(isalpha(matriz[linha][11]) || isdigit(matriz[linha][11])) {
            return 1;
        }
    }
    return 0;
}

/* Argumentos:
 * posicao: inteiro com a posicao da linha atual. 0: esquerda, 1: direita.
 * linha: inteiro com a linha atual.
 * matriz: matriz de caracteres onde esta armazenado o mapa.
 * carac_ender: string contendo os caracteres hexadecimais convertidos do 
 * endereco.
 */
void preenche_endereco(int posicao, int linha, char **matriz,
                       char *carac_ender) {
    if(posicao == 0) {
        matriz[linha][7] = carac_ender[0];
        matriz[linha][8] = carac_ender[1];
        matriz[linha][9] = carac_ender[2];
    } else {
        matriz[linha][14] = carac_ender[0];
        matriz[linha][15] = carac_ender[1];
        matriz[linha][16] = carac_ender[2];
    }
}

/* Argumentos:
 * linha: inteiro com a linha atual.
 * digitos: string contendo os caracteres hexadecimais convertidos.
 * matriz: matriz de caracteres onde esta armazenado o mapa.
 * flag: para o caso de inserir um hexadecimal, flag = 2, ira deslocar o '0x'
 * caso contrario, flag = 0.
 */
void insere_digitos(int linha, char *digitos, char **matriz, int flag) {
    matriz[linha][4] = digitos[0+flag];
    matriz[linha][5] = digitos[1+flag];
    matriz[linha][6] = ' ';
    matriz[linha][7] = digitos[2+flag];
    matriz[linha][8] = digitos[3+flag];
    matriz[linha][9] = digitos[4+flag];
    matriz[linha][10] = ' ';
    matriz[linha][11] = digitos[5+flag];
    matriz[linha][12] = digitos[6+flag];
    matriz[linha][13] = ' ';
    matriz[linha][14] = digitos[7+flag];
    matriz[linha][15] = digitos[8+flag];
    matriz[linha][16] = digitos[9+flag];
}

/* Argumentos:
 * lis: no dummy de uma lista passada.
 */
void libera_memoria(lista *lis) {
    lista *aux = lis;
    while(aux != NULL) {
        lista *next = aux->prox;
        free(aux->palavra);
        free(aux->valor);
        free(aux);
        aux = next; 
    }
}

/* Argumentos:
 * lis: no dummy da lista de rotulos.
 * palavra: stirng a ser procurada na lista. 
 */
lista* percorre_lista(lista *lis, char *palavra) {
    lista *aux = lis->prox;
    while(aux != NULL) {
        if(!strcmp(aux->palavra, palavra)) {
            break;
        }
        aux = aux->prox;
    }
    return aux;
}

/* Argumentos:
 * linha: inteiro com o valor linha atual.
 * posicao: inteiro com a posicao atual. 0: esquerda, 1: direita.
 * matriz: matriz de caracteres onde esta armazenado o mapa.
 * */
void endereco_vazio(int linha, int posicao, char **matriz) {
    /* Inserindo 0's a esquerda no endereco */
    if(posicao == 0) {
        matriz[linha][7] = '0';
        matriz[linha][8] = '0';
        matriz[linha][9] = '0';
    } 
    /* Inserindo 0's a direita no endereco */
    else {
        matriz[linha][14] = '0';
        matriz[linha][15] = '0';
        matriz[linha][16] = '0';
    }
}

/* Argumentos:
 * rot: no dummy da lista de rotulos.
 * sym: no dummy da lista de simbolos.
 *
 * Retorno: 
 * 1: se houve erro.
 * 0: se nao houve erro.
 * */
int pega_rotulos_simbolos(lista *rot, lista *sym) {

    int i;
    int linha = 0, posicao = 0;
    int n_tokens = getNumberOfTokens();
    int status = 0;

    /* Percorre todos os tokens */
    for(i = 0; i < n_tokens; i++) {
        Token atual = recuperaToken(i);
   
        if(atual.tipo == DefRotulo) {
            /* Adiciona na lista de rotulos uma definicao encontrada */
           status = insere_lista(&linha, &posicao, rot, atual.palavra); 
        } 
        /* Caso seja encontrada uma diretiva do tipo ".set" */
        else if(atual.tipo == Diretiva && !strcmp(atual.palavra, ".set")) {
            i += 1; 
            Token next = recuperaToken(i);
            i += 1;
            Token next_next = recuperaToken(i);

            /* Adiciona na lista de simbolos uma definicao encontrada */
            status = insere_lista(&linha, &posicao, sym, next.palavra); 
            insere_lista_simbolo(sym, next_next.palavra); 
        } else if(atual.tipo == Instrucao) {
            /* Se foi encontrada uma instrucao, so troca a posicao */
            change_pos(&posicao, &linha);
        } else if(atual.tipo == Diretiva) {
            /* Caso outra diretiva seja encontrada, movimenta apenas a linha e 
             * a posicao, conforme necessario, sem usar o mapa de memoria  */
            if(!strcmp(atual.palavra, ".org")) {
                i += 1;
                Token next = recuperaToken(i);

                if(next.tipo == Hexadecimal) {
                    char carac_ender[3]; 
                    int tamanho = strlen(next.palavra);
                    int k, j;

                    for(k = tamanho -1, j = 2; j >= 0; k--, j--) {
                        carac_ender[j] = next.palavra[k];
                    }
                    linha = strtol(carac_ender, NULL, 16);
                }
                else if(next.tipo == Decimal) {
                    linha = atoi(next.palavra);
                }
                posicao = 0;
            } else if(!strcmp(atual.palavra, ".align")) {
                i += 1;
                Token next = recuperaToken(i);
                int num = atoi(next.palavra);
                linha = linha + num - (linha % num); 
                posicao = 0;
            } else if(!strcmp(atual.palavra, ".wfill")) {
                i += 1; 
                Token next = recuperaToken(i);
                int num = atoi(next.palavra); 
                i += 1;
                linha += num;
            } else if(!strcmp(atual.palavra, ".word")) {
                i += 1;
                linha += 1;
            }
        } 
    }

    return status;
}

/* Argumentos:
 * rot: no dummy da lista de rotulos.
 * palavra: string a ser colocada na lista de rotulos.
 * */
void insere_lista_simbolo(lista *rot, char *palavra) {

    /* Insercao sempre no inicio da lista */
    rot = rot->prox;
    rot->valor = malloc(sizeof(char)*strlen(palavra) + 1);
    strcpy(rot->valor, palavra);
}

/* Argumentos: 
 * linha: ponteiro para a linha atual.
 * posicao: ponteiro para a posicao atual. 0: esquerda, 1: direita.
 * lis: no dummy de uma lista qualquer que foi passada.
 * palavra: string a ser inserida na lista
 *
 * Retorno:
 * 1: se houve erro.
 * 0: se nao houve erro.
 * */
int insere_lista(int *linha, int *posicao, lista *lis, char *palavra) {

    /* Caso haja um ':' no final da palavra, remova-o */
    if(palavra[strlen(palavra)-1] == ':') {
        palavra[strlen(palavra)-1] = '\0';
    }

    lista *aux = lis->prox;

    /* Procura por palavra na lista*/
    aux = percorre_lista(lis, palavra);

    /* Testa se palavra ja foi inserida  */
    if(aux == NULL) {
        /* Insercao no inicio da lista */
        lista *new = malloc(sizeof(lista));
        new->prox = lis->prox;
        lis->prox = new;
        new->palavra = malloc(sizeof(char)*strlen(palavra) + 1);
        strcpy(new->palavra, palavra);
        new->endereco = *linha;
        new->posicao = *posicao;
        new->valor = NULL;
        return 0;
    }

    return 1;
}

/* Argumentos:
 * i: ponteiro para o numero do token atual.
 * posicao: ponteiro para a posicao atual. 0: esquerda, 1: direita.
 * linha: ponteiro para a linha atual.
 * matriz: matriz de caracteres onde esta armazenado o mapa.
 * rot: no dummy da lista de rotulos. 
 *
 * Retorno:
 * 2: se houve erro do tipo de usado mas nao definido.
 * 1: se houve erro do tipo impossivel de montar o codigo.
 * 0: se nao houve erro.
 * */
int insere_endereco(int *i, int *posicao, int *linha, char **matriz, 
                     lista *rot, char *gp) {

    *i += 1;
    Token endereco = recuperaToken(*i);
    char carac_ender[3]; 

    if(endereco.tipo == Hexadecimal) {
        int tamanho = strlen(endereco.palavra);
        int i, j;
        /* Pega os caracteres relevantes e coloca em um auxiliar */
        for(i = tamanho -1, j = 2; j >= 0; i--, j--) {
            carac_ender[j] = endereco.palavra[i];
        }

        /* Copia esses caracteres na posicao correta do mapa */
        preenche_endereco(*posicao, *linha, matriz, carac_ender);
    } else if(endereco.tipo == Decimal) {
        int num = atoi(endereco.palavra);
        sprintf(carac_ender, "%03X", num);
        preenche_endereco(*posicao, *linha, matriz, carac_ender);
    } 
    /* Caso nao seja um decimal ou hexadecimal */
    else {
        /* Procura na lista de rotulos */
        lista *lis = percorre_lista(rot, endereco.palavra);

        if(lis == NULL) {
            /* Foi utilizado um rotulo ainda nao definido */
            strcpy(gp, endereco.palavra);
            gp[strlen(endereco.palavra)] = '\0';
            return 2;
        } else {
            sprintf(carac_ender, "%03X", rot->endereco); 
            preenche_endereco(*posicao, *linha, matriz, carac_ender);
        }
    }
    return 0;
}

/* Argumentos:
 * i: ponteiro para o numero do token atual.
 * palavra: string contendo os caracteres do token atual.
 * posicao: ponteiro para a posicao atual. 0: esquerda, 1: direita.
 * linha: ponteiro para a linha atual.
 * matriz: matriz de caracteres onde esta armazenado o mapa.
 * rot: no dummy da lista de rotulos.
 * sym: no dummy da lista de simbolos.
 *
 * Retorno:
 *
 * 0: se nao houve erro.
 * 1: se houve erro do tipo impossivel de montar o codigo. 
 * 2: se houve erro do tipo usar um simbolo ou rotulo nao definido.
 * */
int insere_diretiva(int *i, char *palavra, int *posicao, int *linha, 
                    char **matriz, lista *rot, lista *sym, 
                    char *gp) {

    if(!strcmp(palavra, ".set")) {
        *i += 1; 
        Token next = recuperaToken(*i);
        *i += 1;
        Token next_next = recuperaToken(*i);

        insere_lista(linha, posicao, sym, next.palavra); 
        insere_lista_simbolo(sym, next_next.palavra); 
        
    } else if(!strcmp(palavra, ".org")) {

        *i += 1;
        Token next = recuperaToken(*i);

        put_0_right(matriz, *linha, *posicao);
        if(next.tipo == Hexadecimal) {
            char carac_ender[3]; 
            int tamanho = strlen(next.palavra);
            int k, j;

            for(k = tamanho -1, j = 2; j >= 0; k--, j--) {
                carac_ender[j] = next.palavra[k];
            }
            *linha = strtol(carac_ender, NULL, 16);
        }
        else if(next.tipo == Decimal) {
            *linha = atoi(next.palavra);
        }
        *posicao = 0;

        
    } else if(!strcmp(palavra, ".align")) {
        *i += 1;
        Token next = recuperaToken(*i);
        int num = atoi(next.palavra);
        *linha = *linha + num - (*linha % num); 
        *posicao = 0;
    } else if(!strcmp(palavra, ".wfill")) {
        

        if(verifica_matriz(matriz, *linha, *posicao)) {
            return 1;
        }

        if(* posicao == 1) {
            return 1;
        }
        *i += 1; 
        Token next = recuperaToken(*i);
        int num = atoi(next.palavra); 
        int k;
        *i += 1;
        Token next_next = recuperaToken(*i);

        if(next_next.tipo == Decimal) {
            int valor = atoi(next_next.palavra);
            char digitos[10];
            sprintf(digitos, "%010X", valor);
            for(k = 0; k < num; k++, *linha += 1) {
                insere_linha(linha, matriz);
                insere_digitos(*linha, digitos, matriz, 0);
            }
              
        } else if(next_next.tipo == Hexadecimal) {
            for(k = 0; k < num; k++, *linha += 1) {
                insere_linha(linha, matriz);
                insere_digitos(*linha, next_next.palavra, matriz, 2);
            }
        } else {
            /* Procura na lista de rotulos */
            lista *aux = percorre_lista(rot, next_next.palavra);

            /* Pode ser um simbolo */
            if(aux == NULL) {

                /* Procura na lista de simbolos */
                aux = percorre_lista(sym, next_next.palavra);

                /* Se estiver na lista de simbolos */
                if(aux != NULL) {
                    int valor = atoi(aux->valor);
                    char digitos[10];
                    sprintf(digitos, "%010X", valor);
                    for(k = 0; k < num; k++, *linha += 1) {
                        insere_linha(linha, matriz);
                        insere_digitos(*linha, digitos, matriz, 0);
                    }
                }
                /* Usou uma palavra nao definida ao longo do codigo */
                else {
                    strcpy(gp, next_next.palavra);
                    gp[strlen(next_next.palavra)] = '\0';
                    return 2;
                }
            } 
            /* Eh um rotulo */
            else {
                int valor = aux->endereco;
                char digitos[10];
                sprintf(digitos, "%010X", valor);
                for(k = 0; k < num; k++, *linha += 1) {
                    insere_linha(linha, matriz);
                    insere_digitos(*linha, digitos, matriz, 0);
                }
            }
        }
    } else if(!strcmp(palavra, ".word")) {

        if(verifica_matriz(matriz, *linha, *posicao)) {
            return 1;
        }
        
        if(* posicao == 1) {
            return 1;
        }

        *i += 1;
        Token next = recuperaToken(*i);
        int k;

        if(next.tipo == Decimal) {
            int valor = atoi(next.palavra);
            char digitos[10]; 
            sprintf(digitos, "%010X", valor);
            insere_linha(linha, matriz);
            insere_digitos(*linha, digitos, matriz, 0);
              
        } else if(next.tipo == Hexadecimal) {
            insere_linha(linha, matriz);
            insere_digitos(*linha, next.palavra, matriz, 2);

        } else {
            /* Procura na lista de rotulos */
            lista *aux = percorre_lista(rot, next.palavra);

            /* Pode ser um simbolo */
            if(aux == NULL) {
                /* Procura na lista de simbolos */
                aux = percorre_lista(sym, next.palavra); 
                if(aux != NULL) {
                    int valor = atoi(aux->valor);
                    char digitos[10];
                    sprintf(digitos, "%010X", valor);
                    insere_linha(linha, matriz);
                    insere_digitos(*linha, digitos, matriz, 0);
                }
                else {
                    strcpy(gp, next.palavra);
                    gp[strlen(next.palavra)] = '\0';
                    return 2;
                }
            } 
            /* Eh um rotulo */
            else {
                int valor = aux->endereco;
                char digitos[10];
                sprintf(digitos, "%010X", valor);
                insere_linha(linha, matriz);
                insere_digitos(*linha, digitos, matriz, 0);
            }
        }
        *linha += 1;
    }
    return 0;
}

/* Argumentos:
 * linha: ponteiro para a linha atual.
 * matriz: matriz de caracteres onde esta armazenado o mapa.
 * */
void insere_linha(int *linha, char **matriz) {
    char char_linha[3];
    sprintf(char_linha, "%03X", *linha);

    /* Coloca no espaco da linha os caracteres convertidos */
    matriz[*linha][0] = char_linha[0];
    matriz[*linha][1] = char_linha[1];
    matriz[*linha][2] = char_linha[2];
    matriz[*linha][3] = ' ';
}

/* Argumentos:
 * instruc: string com a instrucao a ser colocada.
 * posicao: ponteiro para a posicao atual. 0: esquerda, 1: direita.
 * linha: ponteiro para a linha atual.
 * matriz: matriz de caracteres onde esta armazenado o mapa.
 * */
void insere_instrucao(char *instruc, int *posicao, int *linha, char **matriz) {
    /* Insere a esquerda */
    if(*posicao == 0) {
        matriz[*linha][3] = ' ';
        matriz[*linha][4] = instruc[0];
        matriz[*linha][5] = instruc[1];
        matriz[*linha][6] = ' ';
    }
    /* Insere a direita */
    else {
        matriz[*linha][10] = ' ';
        matriz[*linha][11] = instruc[0];
        matriz[*linha][12] = instruc[1];
        matriz[*linha][13] = ' ';
    }
}

/* Argumentos:
 * posicao: ponteiro para a posicao atual. 0: esquerda, 1: direita.
 * linha: ponteiro para a linha atual.
 * */
void change_pos(int *posicao, int *linha) {
    if(*posicao == 0) {
        *posicao = 1;
    } else {
        *posicao = 0;
        *linha += 1;
    }
}
/* Retorna:
 *  1 caso haja erro na montagem; 
 *  0 caso não haja erro.
 */
int emitirMapaDeMemoria()
{
    int n_tokens = getNumberOfTokens(); 
    int i, j;

    setlocale(LC_ALL, "");

    /* Variavel para controlar a impressao da linha, matriz atual */
    int linha = 0;

    /* Variavel para tratar se estamos na posicao esquerda ou direita da
     * palavra no IAS. Esquerda=0; Direita=1. */
    int posicao = 0;

    /* Variavel para guardar o retorno de funcoes que podem causar erro */
    int status = 0;

    /* Alocando uma matriz com o tamanho maximo possivel */
    char **matriz = malloc(sizeof(char*)*1025);
    for(i = 0; i < 1025; i++) {
        matriz[i] = malloc(18*sizeof(char));
        memset(matriz[i], '.', 16);
        matriz[i][17] = '\0';
    } 

    /* lista de rotulos */
    lista *rot= malloc(sizeof(lista));
    rot->prox = NULL;

    /* lista de simbolos */
    lista *sym = malloc(sizeof(lista));
    sym->prox = NULL;
   
    /* Rotulo ou Simbolo usado mas nao definido */
    char gp[65]; 

    /* A abordagem de montagem utilizada foi em 2 passos, o primeiro passo eh
     * percorrer os tokens e coletar as definicoes de rotulos e simbolos */
    status = pega_rotulos_simbolos(rot, sym);

    /* O segundo passo da montagem eh percorrer os tokens e alinhar as instru-
     * coes, diretivas, etc. */
    for(i = 0; i < n_tokens; i++) {

        Token atual = recuperaToken(i);

        /* Checando se ou simbolo foi definido duas vezes foi inserido  */
        if(status) {
            break;
        }

        /* Descobrindo se eh uma instrucao */ 
        if(atual.tipo == Instrucao) {

            /* Verifica se a nova linha de insercao ja foi utilizada */
            status = verifica_matriz(matriz, linha, posicao);

            if(status) {
                break;
            }

            /* Verificando qual instrucao eh */
            if(!strcmp(atual.palavra, "LOAD")) {
                insere_instrucao("01", &posicao, &linha, matriz);
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            }
            else if (!strcmp(atual.palavra, "LOAD-")) {
                insere_instrucao("02", &posicao, &linha, matriz);
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            } 
            else if (!strcmp(atual.palavra, "LOAD|")) {
                insere_instrucao("03", &posicao, &linha, matriz);
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            }
            else if (!strcmp(atual.palavra, "LOADmq"))  {
                insere_instrucao("0A", &posicao, &linha, matriz);
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            } 
            else if (!strcmp(atual.palavra, "LOADmq_mx")) {
                insere_instrucao("09", &posicao, &linha, matriz);
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            } 
            else if (!strcmp(atual.palavra, "STOR")) {
                insere_instrucao("21", &posicao, &linha, matriz);
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            }
            else if (!strcmp(atual.palavra, "JUMP")) {
                int aux_index = i;
                aux_index += 1;
                Token next = recuperaToken(aux_index);
                if(next.tipo == Nome) {
                    lista *aux_lis = percorre_lista(rot, next.palavra);
                    if(aux_lis == NULL) {
                        strcpy(gp, next.palavra);
                        gp[strlen(next.palavra)] = '\0';
                        status = 2;
                        break;
                    } else if(aux_lis->posicao == 1) {
                        insere_instrucao("0E", &posicao, &linha, matriz);
                    } else {
                        insere_instrucao("0D", &posicao, &linha, matriz);
                    } 
                } else {
                    insere_instrucao("0D", &posicao, &linha, matriz);
                }
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            } 
            else if (!strcmp(atual.palavra, "JMP+")) {
                int aux_index = i;
                aux_index += 1;
                Token next = recuperaToken(aux_index);
                if(next.tipo == Nome) {
                    lista *aux_lis = percorre_lista(rot, next.palavra);

                    if(aux_lis == NULL) {
                        strcpy(gp, next.palavra);
                        gp[strlen(next.palavra)] = '\0';
                        status = 2;
                        break;
                    } else if(aux_lis->posicao == 1) {
                        insere_instrucao("0F", &posicao, &linha, matriz);
                    } else {
                        insere_instrucao("10", &posicao, &linha, matriz);
                    }
                } else {
                    insere_instrucao("0F", &posicao, &linha, matriz);
                }
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            } 
            else if (!strcmp(atual.palavra, "ADD")) {
                insere_instrucao("05", &posicao, &linha, matriz);
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            } 
            else if (!strcmp(atual.palavra, "ADD|")) {
                insere_instrucao("07", &posicao, &linha, matriz);
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            } 
            else if (!strcmp(atual.palavra, "SUB")) {
                insere_instrucao("06", &posicao, &linha, matriz);
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            } 
            else if (!strcmp(atual.palavra, "SUB|")) {
                insere_instrucao("08", &posicao, &linha, matriz);
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            } 
            else if (!strcmp(atual.palavra, "MUL")) {
                insere_instrucao("0B", &posicao, &linha, matriz);
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            } 
            else if (!strcmp(atual.palavra, "DIV")) {
                insere_instrucao("0C", &posicao, &linha, matriz);
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            } 
            else if (!strcmp(atual.palavra, "LSH")) {
                insere_instrucao("14", &posicao, &linha, matriz);
                endereco_vazio(linha, posicao, matriz);
            }
            else if (!strcmp(atual.palavra, "RSH")) {
                insere_instrucao("15", &posicao, &linha, matriz);
                endereco_vazio(linha, posicao, matriz);
            } 
            else if (!strcmp(atual.palavra, "STORA")) {
                int aux_index = i;
                aux_index += 1;
                Token next = recuperaToken(aux_index);
                if(next.tipo == Nome) {
                    lista *aux_lis = percorre_lista(rot, next.palavra);

                    if(aux_lis == NULL) {
                        strcpy(gp, next.palavra);
                        gp[strlen(next.palavra)] = '\0';
                        status = 2;
                        break;
                    } else if(aux_lis->posicao == 1) {
                        insere_instrucao("12", &posicao, &linha, matriz);
                    } else {
                        insere_instrucao("13", &posicao, &linha, matriz);
                    }
                } else {
                    insere_instrucao("12", &posicao, &linha, matriz);
                }
                status = insere_endereco(&i, &posicao, &linha, matriz, rot, gp);
            }

            change_pos(&posicao, &linha);
        }
        /* Caso seja uma diretiva */
        else if(atual.tipo == Diretiva) {
            status = insere_diretiva(&i, atual.palavra, &posicao, &linha, 
                                     matriz, rot, sym, gp); 
        }
      
        /*Ao final de cada instrucao, atualizar a linha a ser utilizada */ 
        insere_linha(&linha, matriz);
    }

    /* Caso tenha parado a montagem na direita (incompleta) */
    put_0_right(matriz, linha, posicao);
    if(status == 0) {
        /* Realizando a impressao do mapa */
        for(j = 0; j < 1025; j++) {
            /* Caso a linha nao tenha sido utilizada, pule-a */
            if(!isalpha(matriz[j][4]) && !isdigit(matriz[j][4])) {
                continue;
            } 
            printf("%s\n", matriz[j]);
        }

        for(i = 0; i < n_tokens; i++) {
            free(recuperaToken(i).palavra);
        }

        libera_memoria(rot);
        libera_memoria(sym);
        
        for(i = 0; i < 1025; i++) {
            free(matriz[i]);
        }
        free(matriz);

        return 0;
    } else {
        for(i = 0; i < n_tokens; i++) {
            free(recuperaToken(i).palavra);
        }

        libera_memoria(rot);
        libera_memoria(sym);
        
        for(i = 0; i < 1025; i++) {
            free(matriz[i]);
        }
        free(matriz);

        if(status == 1) {
            printf("Impossível montar o código!\n");
        }
        else if(status == 2) {
            printf("USADO MAS NÃO DEFINIDO: %s!\n", gp);
        }

        return 1;
    }
}
