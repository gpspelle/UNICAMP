#include "montador.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <locale.h>

#define MAX 2147483648 

void trim();
int check_end_line();
void capturar_palavra();
void processa_palavra();
int check_number();
int is_diretiva();
int is_token_number();
int processa_tokens();
int is_SYM();
int is_DEC();
int is_HEX();
int is_a_number();

/* 
 * Argumentos:
 *  entrada: cadeia de caracteres com o conteudo do arquivo de entrada.
 *  tamanho: tamanho da cadeia.
 * Retorna:
 *  1 caso haja erro na montagem; 
 *  0 caso não haja erro.
 */
int processarEntrada(char* entrada, unsigned tamanho) 
{
    /* Impressao de acentos na saida */
    setlocale(LC_ALL,"");

    /* Index da entrada */
    int ie = 0;
    /* Linha de codigo atual */
    int linha = 1;

    while(ie < tamanho - 1) {

        /* Pulando espacos em brancos e \n com a soma da linha autal */
        do {
            trim(entrada, &ie);
        } while(check_end_line(entrada, tamanho, &ie, &linha));
     
       
        /* Tratando o caso em que o checagem acima acabou com a entrada*/ 
        if(ie >= tamanho -1) {
            break;
        }
        else {
            char *palavra = malloc(sizeof(char)*(tamanho - ie + 1));
            /* Captura a proxima palavra a ser lida */ 
            capturar_palavra(entrada, tamanho, &ie, palavra, &linha);

            /* Processa a palavra lida */
            processa_palavra(palavra, linha);
        }

    }

    if(!processa_tokens()) {
        imprimeListaTokens();
        return 0;
    }
    
    //imprimeListaTokens();

    return 1; 
}

/* 
 * Retorna:
 *  1 caso haja erro no processamento; 
 *  0 caso não haja erro.
 */
int processa_tokens() {
    int i;
    int n_tokens = getNumberOfTokens(); 
    int linha_atual = 0;
    int found_rotulo = 0, found_diretiva = 0;
    int found_instrucao = 0, found_nome = 0;
    
    Token t;

    /* Percorrendo todos os tokens inseridos a partir do primeiro */
    for(i = 0; i < n_tokens; i++) {
        t = recuperaToken(i);
   
        /* Indicando que eu troquei de linha, entao posso reencontrar tokens
         * do mesmo tipo */ 
        if(t.linha != linha_atual) {
            found_rotulo = 0;
            found_diretiva = 0;
            found_instrucao = 0;
            found_nome = 0;
        }

        linha_atual = t.linha;
        
        if(t.tipo == DefRotulo) {

            /* Caso ja tenha sido encontrado um rotulo em uma linha */
            if(found_rotulo || found_diretiva || found_instrucao) {
                fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                return 1;
            }
            else {
                found_rotulo = 1;
            }

            int k = 0;
            int tam_palavra = strlen(t.palavra);
            for(k = 0; k < tam_palavra; k++) {
                /* Verificando se apenas o ultimo caracter eh ':' */
                if(k != (tam_palavra -1) && t.palavra[k] == ':') {
                    fprintf(stderr, "ERRO LEXICO: palavra inválida na linha %d!\n", t.linha);
                    return 1;
                }
                /* Verificando se o primeiro caracter eh um numero */
                else if(isdigit(t.palavra[0])) {
                    fprintf(stderr, "ERRO LEXICO: palavra inválida na linha %d!\n", t.linha);
                    return 1;
                }
            }
        }

        else if(t.tipo == Diretiva) {
            
            /* Caso ja tenha sido encontrada uma diretiva na linha */
            if(found_diretiva || found_instrucao) {
                fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                return 1;
            } else {
                found_diretiva = 1;
            }
            
            /* Caso seja uma diretiva de set, vamos testar os proximos tokens */
            if(!strcmp(t.palavra, ".set")) {
                /* Caso hajam mais dois tokens  */
                if(i < n_tokens - 2) {
                    Token next = recuperaToken(i+1);
                    Token next_next = recuperaToken(i+2);
                    /* O primeiro token tem que ser um nome e o segundo um nu-
                     * mero. Mas o nome nao pode ser uma diretiva */
                    if(!is_SYM(next.palavra) || 
                            !(is_HEX(next_next.palavra) || 
                                is_DEC(next_next.palavra, -MAX, MAX - 1))) {
                        fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                        return 1;
                        
                    }
                } 
                /* Caso nao hajam mais dois tokens */
                else {
                    fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                    return 1;
                }

            } else if(!strcmp(t.palavra, ".org")) {
                /* Caso haja mais um token */
                if (i < n_tokens - 1) {
                    Token next = recuperaToken(i+1);

                    /* O proximo token precisa ser um numero */
                    if(!is_DEC(next.palavra, 0, 1023) && !is_HEX(next.palavra)) {
                        fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                        return 1;
                    }
                }
                /* Caso nao haja mais um token */
                else {
                    fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                    return 1;
                }
            } else if(!strcmp(t.palavra, ".align")) {
                if (i < n_tokens - 1) {
                    Token next = recuperaToken(i+1);
                   
                    /* O proximo token precisa ser um numero decimal */ 
                    if(!is_DEC(next.palavra, 1, 1023)) {
                        fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                        return 1;
                    }
                }
                else {
                    fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                    return 1;
                }

            } else if(!strcmp(t.palavra, ".wfill")) {
                if (i < n_tokens - 2) {
                    Token next = recuperaToken(i+1);
                    Token n_n = recuperaToken(i+2);
                    /* O proximo token precisa ser um numero */ 
                    if(!is_DEC(next.palavra, 1, 1023) || 
                        !(is_HEX(n_n.palavra) 
                            || is_DEC(n_n.palavra, -MAX, MAX - 1) 
                                || is_SYM(n_n.palavra)))  {
                        fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                        return 1;
                    }
                }
                else {
                    fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                    return 1;
                }
            }
        }
        else if(is_token_number(t)) {

            if(!is_HEX(t.palavra) && !is_DEC(t.palavra, -MAX, MAX - 1)) {
                fprintf(stderr, "ERRO LEXICO: palavra inválida na linha %d!\n", t.linha); 
                return 1;
            }

            /* Caso haja mais de um token */
            if(i > 0) {
                Token last = recuperaToken(i-1);
                if(last.tipo == DefRotulo) {
                    fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                    return 1;
                } 
            } else {
                fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                return 1;
            } 
        }
        else if(t.tipo == Nome) {

            if(!is_SYM(t.palavra)) {
                fprintf(stderr, "ERRO LEXICO: palavra inválida na linha %d!\n", t.linha); 
                return 1;
            }
            if(found_nome == 1) {
                fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                return 1;
            } else {
                found_nome = 1;
            }
            if(i > 0) {
                Token last = recuperaToken(i-1);
                if(last.tipo == Instrucao || last.tipo == Diretiva) {
                    if(last.tipo == Diretiva) {
                        if(strcmp(last.palavra, ".set") && strcmp(last.palavra, ".word")) {
                            fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha);
                            return 1;
                        }
                    }
                }
                else if(last.tipo == Decimal) {
                    if(i > 1) {
                        Token last_last = recuperaToken(i-2);
                        if(strcmp(last_last.palavra, ".wfill")) {
                            fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha);
                            return 1;
                        } 
                    }
                }
                else {
                    fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha);
                    return 1;
                }
            } else {
                fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                return 1;
            }
        } else if(t.tipo == Instrucao) {
            if(found_instrucao == 1) {
                fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha); 
                return 1;
            } else {
                found_instrucao = 1;
            }
            if(strcmp(t.palavra, "RSH") && strcmp(t.palavra, "LSH")) {
                if(i < n_tokens - 1) {
                    Token next = recuperaToken(i+1);
                    if(!is_HEX(next.palavra) && !is_DEC(next.palavra, 0, 1023) 
                            && !is_SYM(next.palavra)) {
                        fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha);
                        return 1;
                    }
                } else {
                    fprintf(stderr, "ERRO GRAMATICAL: palavra na linha %d!\n", t.linha);
                    return 1;
                }
            }
        }
    }
    return 0;
}
/* 
 * Argumentos:
 *  next: token a ser analisado.
 * Retorna:
 *  1 caso o token next seja um decimal ou hexadecimal; 
 *  0 caso o token next seja de outro tipo.
 */

int is_token_number(Token next) {

    if(next.tipo != Decimal && next.tipo != Hexadecimal) {
        return 0;
    }
    else {
        return 1;
    }
}

/* 
 * Argumentos:
 * palavra: candidado a numero a ser analisado.
 * Retorna:
 *  2 caso palavra seja um decimal; 
 *  1 caso palavra seja um hexadecimal.
 *  0 caso palavra nao seja um numero.
 */
int is_a_number(char *palavra) {

   int tamanho = strlen(palavra) - 1; 
   int i;
   if(tamanho >= 1) {
        if(palavra[0] == '0' && palavra[1] == 'x') {
            for(i = 2; i < tamanho; i++) {
                if(!isdigit(palavra[i])) {
                    if(palavra[i] < 65) {
                        return 0;
                    } else if(palavra[i] > 90 && palavra[i] < 97) {
                        return 0;
                    } else if(palavra[i] > 123) {
                        return 0;
                    }
                }
            }
            return 1;
        }
        else {
            for(i = 0; i < tamanho; i++) {
                if(!isdigit(palavra[i])) {
                    return 0;
                }
            }
            return 2;
        }
   } else {
        if(isdigit(palavra[0]))
            return 2;
        else {
            return 0;
        }
   }
}

/* 
 * Argumentos:
 * palavra: palavra com caracteres ainda desconhecidos.
 * min: minimo valor possivel
 * max: maximo valor possivel
 * Retorna:
 *  0 caso palavra nao seja um decimal valido.
 *  1 caso palavra seja um decimal valido.
 */
int is_DEC(char *palavra, int min, int max) {

    int tam = strlen(palavra);
    int i = 0;
    if(palavra[0] == '-') {
        i++;
    }

    for(; i < tam; i++) {
        if(!isdigit(palavra[i])) {
            return 0;
        }
    }
    long long int value = atoi(palavra);
    if(value < min || value > max) {
        return 0;
    } 
    return 1;
}

/* 
 * Argumentos:
 * palavra: palavra com caracteres ainda desconhecidos.
 * Retorna:
 *  0 caso palavra nao seja um rotulo ou simbolo valido.
 *  1 caso palavra seja um rotulo ou simbolo valido.
 */
int is_SYM(char *palavra) {

    int tam = strlen(palavra);
    int i;

    if(isdigit(palavra[0])) {
        return 0;
    }

    for(i = 0; i < tam; i++) {
        if(!isdigit(palavra[i]) && !isalpha(palavra[i]) && palavra[i] != '_') {
            return 0;
        }
    }

    return 1;
}
/* 
 * Argumentos:
 * palavra: palavra com caracteres ainda desconhecidos.
 * Retorna:
 *  0 caso palavra nao seja um hexadecimal valido.
 *  1 caso palavra seja um hexadecimal valido.
 */
int is_HEX(char *palavra) {

    int tam = strlen(palavra);
    int i;

    if(tam != 12) {
        return 0;
    }

    if(palavra[0] == '0' && palavra[1] == 'x') {
        for(i = 2; i < tam; i++) {
            if(!isdigit(palavra[i])) {
                if(palavra[i] < 65) {
                    return 0;
                } else if(palavra[i] > 90 && palavra[i] < 97) {
                    return 0;
                } else if(palavra[i] > 123) {
                    return 0;
                }
            }
        }
        return 1;
    } else {
        return 0;
    }
    return 1;
}

int is_diretiva(char *palavra) {

    if(!strcmp(palavra, ".set") || !strcmp(palavra, ".org")
        || !strcmp(palavra, ".align") || !strcmp(palavra, ".wfill")
         || !strcmp(palavra, ".word")) {
            return 1;
    }
    else {
        return 0;
    }
}
/* 
 * Argumentos:
 *  palavra: palavra com caracteres ainda desconhecidos.
 *  linha: linha atual sendo processada.
 */
void processa_palavra(char *palavra, int linha) {

    int tam = strlen(palavra);
    int n_last = tam-1;
    char c_last = palavra[n_last];
    char c_first = palavra[0];
    int n_tokens = getNumberOfTokens();
    Token last_token; 
    Token penul_token;

    if(n_tokens > 0) {
        last_token = recuperaToken(n_tokens-1);
        if(n_tokens > 1) {
            penul_token = recuperaToken(n_tokens-2);
        }
    }
    int i;
    Token novo_token;
    novo_token.linha = linha;
    
    if(c_first == '"' && c_last == '"') {
        for(i = 0; i < tam - 2; i++) {
            palavra[i] = palavra[i+1]; 
        }
        palavra[tam-2] = '\0'; 
    }

    novo_token.palavra = palavra; 
   
    /* A palavra eh uma diretiva */
    if(is_diretiva(palavra)) {
        novo_token.tipo = Diretiva; 
        adicionarToken(novo_token);
    }
    /* A palavra eh um comentario */
    else if(c_first == '#') {
       /* Else meio bobo, porque comentarios nao sao adicionados */ 
    }
    /* Caso seja um termo entre aspas */ 
    else if(c_first == '"' && c_last == '"') {
        /* Eh um numero entre aspas */
        if(is_DEC(palavra, -MAX, MAX -1)) {
            /* Eh um decimal */
            novo_token.tipo = Decimal;
            adicionarToken(novo_token);
        } else if(is_HEX(palavra)) {
            /* Eh um hexadecimal */
            novo_token.tipo = Hexadecimal;
            adicionarToken(novo_token);
        }
        /* Eh um nome */
        else {
            novo_token.tipo = Nome;
            adicionarToken(novo_token);
        }
    }
    
    /* A palavra eh um rotulo */
    else if(c_last == ':') {
        novo_token.tipo = DefRotulo;
        adicionarToken(novo_token);
    }
    else if(!strcmp(palavra, "LOAD")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);
    }
    else if(!strcmp(palavra, "LOAD-")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "LOAD|")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "LOADmq")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "LOADmq_mx")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "STOR")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "JUMP")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "JMP+")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "ADD")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "ADD|")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "SUB")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "SUB|")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "MUL")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "DIV")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "LSH")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "RSH")) {
        novo_token.tipo = Instrucao;
        adicionarToken(novo_token);

    }
    else if(!strcmp(palavra, "STORA")) {
