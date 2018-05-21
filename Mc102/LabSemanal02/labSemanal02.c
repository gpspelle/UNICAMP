   /* Nome: Gabriel Pellegrino da Silva  RA: 172358.
    * Turma: S
    * O programa a seguir leva em considera��es fatores como as dimens�es de uma maquete, das ilhas em seu interior, 
    * o ano ser bissexto, o anivers�rio da cidade, quanto tempo a exposi��o ficar� aberta e o pre�o dos fornecedores
    * para calcular o gasto da cidade com a compra da �gua pra manter a maquete nas exposi��es.
	* 
    */
	  
   	#include <stdio.h> 
  
   int main(int argc, char* argv[]) { 
	   //Declarando vari�veis relacionadas ao c�lculo do volume de �gua por evento
	   int K;
	   double maiorx, maiory, menorx, menory;
	   double x1, x2, x3, y1, y2, y3;
	   double det=0, areatriang, areatotal=0;
	   double aream=0, volm=0, vol=0, vol_agua=0;
	   
	   //Declarando vari�veis auxiliares
	   int j=0, i=0;
	   
	   //Declarando vari�veis relacionadas ao c�lculo do n�mero de eventos
	   int ano=0, bis=0, diaS=4, eventos=0, dia, mes; // diaS=4, pois o primeiro dia 13 de 2016 � uma quarta, sendo o domingo o diaS=1. 
	   
	   //Declarando vari�veis relacionadas aos fornecedores e o gasto final
	   int F;
	   double gasto=0, menorgasto=0;
	   double c, P;
	   
	   //Leitura do n�mero de ilhas (K)
	   	scanf("%d", &K);
	 
	   	//Estabelecendo os limites das ilhas
	   	maiorx = -1000;
	   	maiory = -1000;
	   	menory = 1000;
	  	menorx = 1000;
	   
	  	for(j=1;j<=K;j++) {   
		   	scanf(" (%lf , %lf)", &x1, &y1);    
		   	scanf(" (%lf , %lf)", &x2, &y2);
		   	scanf(" (%lf , %lf)", &x3, &y3);
		    
		    /* 
			 * Descobrindo o maior e o menor x e y, entre 
		     * os 3 primeiros pares de dados fornecidos 
		     */
		  	if (x1>maiorx ) {
		   		maiorx = x1; 
			}
			if (x2>maiorx) {
		   		maiorx = x2;
			}
		   	if (x3>maiorx) {
		  		maiorx = x3; 
			}
		    if(y1>maiory) {
				maiory = y1;
		    }
		    if (y2>maiory) {
		   		maiory = y2;
		    }
		   	if (y3>maiory) {
		   		maiory = y3;
		    }   
		    if (y1<menory) {
		   		menory = y1;
		    }
		    if (y2<menory) {
		    	menory = y2; 
		    }
		   	if (y3<menory) {
		   		menory = y3; 
		   	}
		    if (x1<menorx) {
		   		menorx = x1; 
		   	}
		   	if (x2<menorx) {
		   		menorx = x2; 
		    }
		   	if (x3<menorx) {
		  		menorx = x3;
		    }
		   
		   //C�lculo da �rea das ilhas
		   while(1)  {
		   		det = (x1*y2 + x3*y1 + x2*y3 - x3*y2 - x2*y1 - x1*y3);
		       		//Resolvendo o problema do determinante negativo
		        if(det<0) { 
		        	det = det*(-1);
				} 
		   		areatriang = det/2.0;
		   		areatotal = areatriang + areatotal; 
		   	
		   	 	//Rotacionando o tri�ngulo a ser calculado
			 	x2 = x3;
			 	y2 = y3;
			  
			    //Leitura do novo ponto a ser calculado
				scanf(" (%lf , %lf)", &x3, &y3);
					 
				if( (x3==x1) && (y3==y1) ) {
				 	break; //Parando o processo quando encontrar dois pontos iguais
				}
				if (x3>maiorx) {
		        	maiorx = x3;
				}		
		        if (y3>maiory) {
		        	maiory = y3; 
				}
		      	if (y3<menory) {
		     		menory = y3; 
			    }
		  		if (x3<menorx) {
		      		menorx = x3;
			    }				
			}
		}
	   //C�lculo dos pontos extremos da maquete
	maiorx += 0.5;
	maiory += 0.5;
    menory -= 0.5;
    menorx -= 0.5;
	   
    //C�lculo da �rea(aream) e do volume da maquete(volm)
    aream = (maiorx - menorx) * (maiory - menory);
    if (aream<0) {
   		aream = aream*(-1);
    } 
    volm = aream * 0.50;
     
    //C�lculo do volume das ilhas(vol) 
    vol = areatotal * 0.50;      
   
    //C�lculo do volume a ser preenchido com �gua(vol_agua)
    vol_agua = (volm - vol)*1000;
	
	//Leitura do dia e m�s de anivers�rio da cidade
	scanf("%d/%d", &dia, &mes);
	
	//Leitura de at� que ano a exposi��o se abrir�
 	scanf("%d", &ano);
	 	
	for(i=2016; i<=ano; i++) { 
		
	    /* Descobrindo se o ano � ou n�o bissexto.
	     * A cidade comemora anivers�rio todo ano bissexto. 
	     * E s� n�o o faz, caso seu aniers�rio seja dia 29/02. 
	     * Quando ela o comemora a cada quatro anos. 
	     */
	     
		if( (i%400==0) ) { 
			bis=1;
			eventos++;
		}else if( (i%4==0) && (i%100!=0) ) {
				bis=1; 
				eventos++;
			}else { 
				bis=0;
				if( (dia==29) && (mes==2) ) {
				}else {
					eventos++;	
			}
		}
		for(j=1;j<=12;j++) { 
			if(diaS==6) {
		    //Adicionando um evento, a cada sexta feira 13 
				eventos++;
			}
			if( (j==1) || (j==3) || (j==5) || (j==7) || (j==8) || (j==10) || (j==12) ) {
				diaS = diaS + 31%7;
				if(diaS>7) {  
					diaS=(diaS%7);
				}
			}else if( (j==4) || (j==6) || (j==9) || (j==11) ) {
				diaS = diaS + 30%7;
				if(diaS>7) {
					diaS=(diaS%7);
				}	
			}else if(j==2) { 
				if(bis==1) {
					diaS = diaS + 29%7;
					if(diaS>7) {
						diaS=(diaS%7);
					}else if (bis==0) {
						diaS = diaS;		
					}
				} 
			}
		} //Diminuindo o n�mero de eventos em 1, caso a �gua seja reutilizada
		if( (dia==12) && (mes=j) && (diaS==6) ) { 
			eventos--;
		}else if( (dia==13) && (mes==j) && (diaS==6) ) { 
			eventos--;
		}else if( (dia==14) && (mes=j) && (diaS==6) ) { 
			eventos--;
		}
	}
	//Leitura da quantidade de fornecedores de �gua (F)
    scanf("%d", &F);
	   
    for(i=0; i<F; i++) { 
	   
       //Leitura dos pre�os(P) e volume dos gal�es (c)
	   scanf(" %lf $%lf", &c, &P);
	   
	   //C�lculo do valor a ser pago pela �gua(gasto)
	    gasto = vol_agua/c;
	 	if( (double)gasto > (int)(gasto) ) {
	    	gasto++;
	    	gasto = (int)(gasto);
	    } 
	    gasto = gasto*P*eventos;
	    //Descobrindo qual ser� o menor gasto
	    if(i==0) {
	   		menorgasto = gasto;
		}
	   	if(gasto<menorgasto) {
	   		menorgasto = gasto;
	    }
   	}
	
	//Imprimindo a sa�da
	printf("A manutencao da maquete custara $%.2f aos cofres publicos.\n", menorgasto);
	   
	return 0;
 }
