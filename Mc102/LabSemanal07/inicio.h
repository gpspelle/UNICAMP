#include <stdio.h> 
#include <stdlib.h>
#include <string.h>

//TYPEDEF DAS ESTRUTURAS UTILIZADAS AO LONGO DO PROGRAMA
typedef char string[101];
typedef struct{ 
	char cid[3];
	string nomeDoenca;
	}Doencas; 
typedef struct{
	long int crm;
	string nomeMedico;
	}Medicos;
typedef struct consulta{
	string paciente;
	string data;
	int cid[3];
	long int crm;
	struct consulta *prox; 
	struct consulta *tras;
	}listConsulta;
typedef struct{
	listConsulta *inicio;
	listConsulta *anterior;
	listConsulta *fim;
	int qntd;
	}Controle;
typedef struct{
	int base;
	listConsulta *posicao;
	}lista;
