//FUNCAO QUE LE O NOME DAS DOENCAS E SEU CODIGO CID
void le_doencas(int numDoencas, Doencas *pointerDoencas) {
	int i;
	for(i=0; i<numDoencas; i++)	{
		scanf(" %c%c%c", &(pointerDoencas+i)->cid[0], &(pointerDoencas+i)->cid[1], &(pointerDoencas+i)->cid[2]);
		scanf(" %[^\n]s", (pointerDoencas+i)->nomeDoenca);
	}
}
//FUNCAO QUE LE O NOME DOS MEDICOS E SEU CRM
void le_medicos(int numMedicos, Medicos *pointerMedicos) {
	int i;
	for(i=0; i<numMedicos; i++) {
		scanf(" %ld", &(pointerMedicos+i)->crm);
		scanf(" %[^\n]s", (pointerMedicos+i)->nomeMedico);
	}
}
//FUNCAO QUE LE UMA MATRIZ 2x2
int le_matriz(int *pointerMatriz) {
	int i;
	for(i=0; i<4; i++)
		scanf(" %d", (pointerMatriz+i));
	return (*pointerMatriz)*(*(pointerMatriz+3)) - (*(pointerMatriz+1))*(*(pointerMatriz+2));
}
//ESSA FUNCAO EH A ULTIMA PARTE DE UMA SEQUENCIA DE FUNCOES QUE DESCRIPTOGRAFA O NOME
//ESSA FUNCAO EH CHAMADA DIRETAMENTE APENAS QUANDO QUEREMOS CODIFICAR UM NOME
void decodifica_valor(int *pointera, int letra1, int letra2, int *valores) {
	/* Aqui, subtrai-se 65 das duas primeiras letras, multiplicasse uma matriz coluna
	contendo as letras pela matriz 2x2 modificada e multiplicada pelo inverso. Para 
	depois somar 65 e obter a letra correspondente descriptografada.		 */ 
	int aux1, aux2;
	letra1-=65;
	letra2-=65;
	aux1 = letra1;
	aux2 = letra2; 
	letra1 = *(pointera)*aux1 + *(pointera+1)*aux2;
	letra2 = *(pointera+2)*aux1 + *(pointera+3)*aux2;
	while(letra1>=26)
		letra1-=26;
	while(letra2>=26)
		letra2-=26;
	*valores = letra1+65;
	*(valores+1) = letra2+65;
}
//FUNCAO QUE MODIFICA A MATRIZ 2x2 COMO DITO NO ENUNCIADO E TAMBEM FAZ O MODULO 26 COM OS VALORES
//DA MATRIZ MULTIPLICADOS PELO INVERSO
void decodifica_inversa(int inverso, int *pointerMatriz, int letra1, int letra2, int *valores) {
	int i;
	int *pointera = (int *)malloc(sizeof(int)*4);
	*pointera = inverso*(*(pointerMatriz+3)); 
	*(pointera+1) = -inverso*(*(pointerMatriz+1));
	*(pointera+2) = -inverso*(*(pointerMatriz+2));
	*(pointera+3) = inverso*(*pointerMatriz);
	for(i=0; i<4; i++)	{	
		if(*(pointera+i)>0)
			while(*(pointera+i)>=26)
				*(pointera+i)-=26;
		else
			while(*(pointera+i)<0)
				*(pointera+i)+=26;
	}
	decodifica_valor(pointera, letra1, letra2, valores); 
}	
//FUNCAO QUE CALCULA O INVERSO, ESSA FUNCAO EH CHAMADA TODA VEZ QUE QUEREMOS
//DESCODIFICAR UM NOME
void calcula_inverso(int letra1, int letra2, int determinante, int *pointerMatriz, int *valores) {
	/*PARA CALCULAR O INVERSO, TESTAMOS DE NUMERO EM NUMERO, ATE QUE O PRODUTO ENTRE ELE E O
	DETERMINANTE, TIRADO O MODULO 26, SEJA 1 						*/
	int auxiliar;
	int inverso=0, para=1;
	do {
		inverso++;
		auxiliar = determinante*inverso;
		if(auxiliar>=26) 
			while(auxiliar>=26)
				auxiliar-=26;
		else 
			while(auxiliar<0)
				auxiliar+=26;
		if(auxiliar==1)
			para = 0; 
	}while(para);
	decodifica_inversa(inverso, pointerMatriz, letra1, letra2, valores);
}
//FUNCAO QUE BUSCA UM PACIENTE NA LISTA DE CONSULTA E RETORNA A SUA POSICAO	
listConsulta* busca_paciente(Controle *ctrl, string paciente) {
	listConsulta *aux = (listConsulta *)malloc(sizeof(listConsulta));
	aux=ctrl->inicio; 
	while(1) {
		if(!strcmp(aux->paciente, paciente))
			return aux; 
		aux=aux->prox; 
	} 
	return aux;
}
//FUNCAO UTILIZADA PARA REMOVER UM DETERMINADO PACIENTE
void busca_ponteiro(Controle *ctrl, listConsulta *posicao) { 
	/* PARA REMOVER UM PACIENTE, FAZEMOS COM ELE FIQUE ISOLADO, OU SEJA, 
	SEM NENHUMA CONEXÃO COM A LISTA LIGADA E DEPOIS LIBERAMOS A SUA POSICAO */
	listConsulta *aux= (listConsulta *)malloc(sizeof(listConsulta));
	aux = ctrl->inicio;
	while(1) {
		if(aux==posicao) {	
			if(aux==ctrl->inicio) {
				ctrl->inicio = aux->prox;
				ctrl->anterior = aux->tras;
			}
			else if(aux==ctrl->fim) {
				ctrl->fim = aux->tras;
				ctrl->anterior = aux->tras;
			}	
			aux->tras->prox = aux->prox;
			aux->prox->tras = aux->tras; 
			free(aux);
			return; 
		}
		aux=aux->prox;
	}			
}
//FUNCAO QUE BUSCA UMA DETERMINADA DOENCA NO VETOR DE DOENCAS
Doencas* busca_doenca(int numDoencas, string doenca, Doencas *pointerDoencas) {
	int i;
	for(i=0; i<numDoencas; i++)
		if(!strcmp(doenca, (pointerDoencas+i)->nomeDoenca))
			return (pointerDoencas+i);
	return pointerDoencas;
}
//FUNCAO QUE BUSCA UM MEDICO NO VETOR DE MEDICOS
Medicos* busca_medico(int numMedicos, string medico, Medicos *pointerMedicos) {
	int i;
	for(i=0; i<numMedicos; i++)
		if(!strcmp(medico, (pointerMedicos+i)->nomeMedico))
			return (pointerMedicos+i);
	return pointerMedicos;
}
//ESSA FUNCAO ADICIONA UM MEMBRO NA LISTA
void operacao_add(int determinante, int *pointerMatriz, Controle *ctrl) {
	/*PARA ADICIONAR UM MEMBRO NA LISTA PRECISAMOS DESCODIFICAR O SEU NOME,
	SEGUINDO OS METODOS DO ENUNCIADO. PARA DEPOIS ALOCARMOS UMA POSICAO PARA
	ESSE PACIENTE E INSERI-LO NA LISTA DUPLAMENTE ENCADEADA CIRCULAR COM O NOME CODIFICADO.  */
	listConsulta *aux;
	char *codificado = (char *)malloc(sizeof(string));
	char *verifica = (char *)malloc(sizeof(string));
	int *valores = (int *)malloc(sizeof(int)*2);
	int i=2, j, para =1 ;
	char letra1, letra2;
	char *decodificado = (char *)malloc(sizeof(string)); 
	char *cid_ = (char *)malloc(sizeof(char)*3);
	char *data = (char *)malloc(sizeof(string));
	long int *crmo =(long int *)malloc(sizeof(long int));
	scanf(" %c", &letra1);
	scanf("%c", &letra2);
	*(codificado) = letra1;
	*(codificado+1) = letra2;
	calcula_inverso(letra1, letra2, determinante, pointerMatriz, valores);
	*(decodificado) = *(valores); 
	*(decodificado+1) = *(valores+1);
	if(ctrl->qntd!=0)
		aux = ctrl->inicio;
	while(1) {	
		scanf("%c", &letra1);
		if(letra1==' ')
			break;
		scanf("%c", &letra2);
		*(codificado+i) = letra1;
		*(codificado+i+1) = letra2;
		calcula_inverso(letra1, letra2, determinante, pointerMatriz, valores);
		*(decodificado+i) = *(valores); 
		*(decodificado+i+1) = *(valores+1);
		i+=2;
	}
	*(decodificado+i) = '\0'; 
	*(codificado+i) = '\0';
	scanf("%s ", data);
	*cid_ = getchar(); *(cid_+1) = getchar(); *(cid_+2) = getchar();
	scanf(" %ld", crmo);

	listConsulta *paciente = (listConsulta *)malloc(sizeof(listConsulta)); 
	i=0;
	for(i=0; i<ctrl->qntd && para; i++) {
		for(j=0; j<strlen(aux->paciente); j+=2) {
			calcula_inverso(aux->paciente[j], aux->paciente[j+1], determinante, pointerMatriz, valores);
			*(verifica+j) = *valores;
			*(verifica+j+1) = *(valores+1);
			
		}
		*(verifica+j) = '\0';
		if(strcmp(decodificado, verifica)<0)
			para=0;
		if(para)			
			aux = aux->prox;	
	}
	strcpy(paciente->data, data); 
	strcpy(paciente->paciente, codificado);
	paciente->cid[0] = *cid_; (paciente)->cid[1] = *(cid_+1); (paciente)->cid[2] = *(cid_+2);
	paciente->crm = *crmo; 
	if(ctrl->qntd==0) {
		ctrl->fim = paciente;
		ctrl->inicio = paciente;
		paciente->prox = paciente;
		paciente->tras = paciente;
	}
	ctrl->qntd++;
	if(para && ctrl->qntd>1) {
		aux = aux->tras;
		paciente->tras = ctrl->fim;
		ctrl->fim = paciente;
		paciente->prox = ctrl->inicio;
		ctrl->inicio->tras = paciente;
		aux->prox = paciente; 
	}else if(i==1 && ctrl->qntd>1) {
		ctrl->inicio = paciente; 
		paciente->prox = aux;
		paciente->tras = ctrl->fim;
		aux->tras->prox = paciente;  
		aux->tras = paciente; 
	}else if(ctrl->qntd>1) {
		aux->tras->prox = paciente; 
		paciente->tras = aux->tras; 
		aux->tras = paciente; 
		paciente->prox = aux;
	}
	ctrl->anterior = paciente; 
}
//FUNCAO QUE IMPRIME A LISTA DE CONSULTAS
void imprime_lista(Controle *ctrl, char *letraInicial, int *pointerMatriz, int determinante) {
	/*PRECISAMOS DESCOBRIR ONDE COMECAMOS A IMPRIMIR. PARA ISSO, COMPARAMOS 
	A PRIMEIRA LETRA DOS NOMES A LETRA INICIAL.	
	E PELO VALOR DESSA COMPARACAO, SABEMOS ONDE COMECAR.			  */
	int i, j;
	int negativo=-100;
	int positivo=100;
	listConsulta *imprime;
	int entrou = 1; 
	char *decodificado = (char *)malloc(sizeof(string));
	int *valores = (int *)malloc(sizeof(int)*2);
	listConsulta *aux = ctrl->inicio;
	lista *procura = (lista *)malloc(sizeof(lista)*(ctrl->qntd+2));
	for(i=0; i<ctrl->qntd+1; i++) { 
		for(j=0; j<strlen(aux->paciente); j+=2) {
			calcula_inverso(aux->paciente[j], aux->paciente[j+1], determinante, pointerMatriz, valores);
			*(decodificado+j) = *valores;
			*(decodificado+j+1) = *(valores+1);
		}
		*(decodificado+j) = '\0';
		(procura+i)->base = strcmp(decodificado, letraInicial);
		(procura+i)->posicao = aux; 
		aux = aux->prox;
	}
	i=0;
	while(i<ctrl->qntd) {
		if((procura+i)->base>negativo && (procura+i)->base<0 && entrou) {
			imprime = (procura+i)->posicao;
			negativo = (procura+i)->base;
		}else if((procura+i)->base>0) {
			imprime = (procura+i)->posicao;
			break;
		}else if((procura+i)->base<positivo && (procura+i)->base>0) {
			imprime = (procura+i)->posicao;
			positivo = (procura+i)->base;
			entrou = 0 ;
		}i++;
	}aux = imprime;
	do {	
		for(j=0; j<strlen(aux->paciente); j+=2) {
			calcula_inverso(aux->paciente[j], aux->paciente[j+1], determinante, pointerMatriz, valores);
			*(decodificado+j) = *valores;
			*(decodificado+j+1) = *(valores+1);
		}
		*(decodificado+j) = '\0';
		printf("%s %s %c%c%c %ld\n", decodificado, aux->data, aux->cid[0], aux->cid[1], aux->cid[2], aux->crm);
		aux = aux->prox;
		if(aux!=imprime)
			free(aux->tras);
	}while(aux!=imprime);
} 
//FUNCAO QUE REMOVE UM MEMBRO DA LISTA	
void operacao_rm(Controle *ctrl, int *pointerMatriz) { 
	/*PARA ISSO, CHAMA-SE OUTRAS DUAS FUNCOES QUE VAO ENCONTRAR A POSICAO
	DO MEMBRO A SER ELIMINADO E DEPOIS ELIMINA-LO			      */
	int i;
	listConsulta *posicao = (listConsulta *)malloc(sizeof(listConsulta));
	char *paciente =(char *)malloc(sizeof(string));
	char *codificado = (char *)malloc(sizeof(string));
	int *valores = (int *)malloc(sizeof(int)*2);
	scanf(" %s", paciente);
	for(i=0; i<strlen(paciente); i+=2) {
		decodifica_valor(pointerMatriz, *(paciente+i), *(paciente+i+1), valores);
		*(codificado+i) = *(valores);
		*(codificado+i+1) = *(valores+1);
	}
	i+=2;
	*(codificado+i) = '\0';
	posicao = busca_paciente(ctrl, codificado);
	busca_ponteiro(ctrl, posicao);
	ctrl->qntd--;
}
//AS TRES FUNCOES A SEGUIR SIMPLESMENTE ALTERAM O DIAGNOSTICO, 
//A DATA DA CONSULTA, OU O MEDICO RESPONSAVEL POR UM PACIENTE
void operacao_diag(int numDoencas, Doencas *pointerDoencas, Controle *ctrl, int *pointerMatriz) {
	int i;
	char *paciente = (char *)malloc(sizeof(string)); 
	char *doenca = (char*)malloc(sizeof(string));
	Doencas *posicaoDoenca = (Doencas *)malloc(sizeof(Doencas));
	listConsulta *posicaoPaciente = (listConsulta *)malloc(sizeof(listConsulta));
	char *codificado = (char *)malloc(sizeof(string));
	int *valores = (int *)malloc(sizeof(int)*2);
	scanf(" %s", paciente);
	for(i=0; i<strlen(paciente); i+=2) {
		decodifica_valor(pointerMatriz, *(paciente+i), *(paciente+i+1), valores);
		*(codificado+i) = *(valores);
		*(codificado+i+1) = *(valores+1);
	}
	i+=2;
	*(codificado+i) = '\0';
	scanf(" %[^\n]s", doenca);
	posicaoDoenca = busca_doenca(numDoencas, doenca, pointerDoencas);
	posicaoPaciente = busca_paciente(ctrl, codificado);
	posicaoPaciente->cid[0] = posicaoDoenca->cid[0];
	posicaoPaciente->cid[1] = posicaoDoenca->cid[1];
	posicaoPaciente->cid[2] = posicaoDoenca->cid[2];
}
void operacao_data(Controle *ctrl, int *pointerMatriz) {
	int i;
	char *paciente = (char *)malloc(sizeof(string));
	listConsulta *posicao = (listConsulta *)malloc(sizeof(listConsulta)); 
	char *data = (char *)malloc(sizeof(string));
	char *codificado = (char *)malloc(sizeof(string));
	int *valores = (int *)malloc(sizeof(int)*2);
	scanf(" %s", paciente);
	for(i=0; i<strlen(paciente); i+=2) {
		decodifica_valor(pointerMatriz, *(paciente+i), *(paciente+i+1), valores);
		*(codificado+i) = *(valores);
		*(codificado+i+1) = *(valores+1);
	}
	*(codificado+i) = '\0';
	scanf(" %s", data);
	posicao = busca_paciente(ctrl, codificado);
	strcpy(posicao->data, data);
}
void operacao_med(Controle *ctrl, int numMedicos, Medicos *pointerMedicos, int *pointerMatriz) {
	int i;
	char *paciente = (char *)malloc(sizeof(string));
	char *medico = (char *)malloc(sizeof(string));
	listConsulta *posicaoPaciente = (listConsulta *)malloc(sizeof(listConsulta));
	Medicos *posicaoMedico = (Medicos *)malloc(sizeof(Medicos));
	char *codificado = (char *)malloc(sizeof(string));
	int *valores = (int *)malloc(sizeof(int)*2);
	scanf(" %s", paciente);
	for(i=0; i<strlen(paciente); i+=2) {
		decodifica_valor(pointerMatriz, *(paciente+i), *(paciente+i+1), valores);
		*(codificado+i) = *(valores);
		*(codificado+i+1) = *(valores+1);
	}
	*(codificado+i) = '\0';
	scanf(" %[^\n]s", medico);
	posicaoMedico = busca_medico(numMedicos, medico, pointerMedicos);
	posicaoPaciente = busca_paciente(ctrl, codificado);
	posicaoPaciente->crm = posicaoMedico->crm; 
}
