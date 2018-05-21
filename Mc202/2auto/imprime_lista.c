void imprimir(Tcabeca *cabeca) {

	Tno *auxiliar;
	auxiliar = cabeca->inic;
	do {
		printf("%d ", auxiliar->num); 
		auxiliar = auxiliar->prox;
	}while(auxiliar->prox!=NULL);
	auxiliar = cabeca->inic;
	do {
		printf("%hi ", auxiliar->count); 
		auxiliar = auxiliar->prox;
	}while(auxiliar->prox!=NULL);
	printf("\n");
	
	return;
}
