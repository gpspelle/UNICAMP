/*
* Autor: Gabriel Pellegrino da Silva
* Ra: 172358
*
* O codigo abaixo trata o processamento de jogadas, atualizacao da vida dos lacaios e dos herois.
* Uma particularidade desse codigo eh que se a verbosidade for mudada para 2 na Main, teremos
* um acompanhamento muito mais detalhado do estado do jogo.
*
* Pergunta Extra:
*
* A classe UnoptimizedDeepCopy ajuda a salvar os valores reais dos atributos dos lacaios e magias
* a fim de evitar que algum jogador mal intencionado consiga alterá-los de alguma maneira e passar
* despercebido. Pois, ao termos salvos os valores reais, podemos sempre conferir com os presentes nas
* cartas ou simplesmente ignorar o objeto passado e apenas utilizar o seu ID para recuperar os valores
* verdadeiros dos atributos/objeto verdadeiro.
*
* Completude do codigo:
*
* Sobre a completude do codigo abaixo, ele possui todas as caracteristicas necessarias para o laboratorio 11, e
* algumas do trabalho 2. As movimentacoes necessarias sobre os efeitos estao implementadas, e tambem a utilizacao
* de algumas taticas para evitar fraudes dos jogadores alterarem os valores das suas cartas.
* No entanto, nao estao implementadas todas as tecnicas de atualizacao do objeto umaJogada recebido no processador,
* com isso, fraudes, que nao estavam previstas no laboratorio 11, podem ocorrer
* Nao estao previstas tambem todas as estruturas para tratamento dos efeitos, visto que nao seria a intencao desse
* laboratorio cuidar dessa parte.
*
*  O trabalho 2, implementa esses trechos faltantes para o motor.
*
* */

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;

public class MotorRA172358 extends Motor {

	public MotorRA172358(Baralho deck1, Baralho deck2, ArrayList<Carta> mao1,
                         ArrayList<Carta> mao2, Jogador jogador1, Jogador jogador2,
                         int verbose, int tempoLimitado, PrintWriter saidaArquivo, EnumSet<Funcionalidade> funcionalidadesAtivas) {
		super(deck1, deck2, mao1, mao2, jogador1, jogador2, verbose,
				tempoLimitado, saidaArquivo, funcionalidadesAtivas);

		/* Variavel para imprimir informacoes adicionais, caso pedido */
		this.verbose = verbose;

		/* Setando como true as funcionalidades ativas */
		if(funcionalidadesAtivas.contains((Funcionalidade.INVESTIDA))) {
			investidaLigada = true;
		}
		if(funcionalidadesAtivas.contains((Funcionalidade.ATAQUE_DUPLO))) {
			ataqueDuploLigado = true;
		}
		if(funcionalidadesAtivas.contains((Funcionalidade.PROVOCAR))) {
			provocarLigado = true;
		}

		imprimir("========================");
		imprimir("*** Classe "+this.getClass().getName()+" inicializada.");
		imprimir("Funcionalidade ativas no Motor:");
		imprimir("INVESTIDA: "+(this.funcionalidadesAtivas.contains(Funcionalidade.INVESTIDA)?"SIM":"NAO"));
		imprimir("ATAQUE_DUPLO: "+(this.funcionalidadesAtivas.contains(Funcionalidade.ATAQUE_DUPLO)?"SIM":"NAO"));
		imprimir("PROVOCAR: "+(this.funcionalidadesAtivas.contains(Funcionalidade.PROVOCAR)?"SIM":"NAO"));
		imprimir("========================");
	}

	private boolean investidaLigada;
	private boolean ataqueDuploLigado;
	private boolean provocarLigado;

	private int verbose;
	private int jogador; // 1 == turno do jogador1, 2 == turno do jogador2.
	private int turno;
	private int nCartasHeroi1;
	private int nCartasHeroi2;
	
	private Mesa mesa;
	
	// "Apontadores" - Assim podemos programar genericamente os métodos para funcionar com ambos os jogadores
	private ArrayList<Carta> mao;
	private ArrayList<Carta> lacaios;
	private ArrayList<Carta> lacaiosOponente;
	
	// "Memória" - Para marcar ações que só podem ser realizadas uma vez por turno.
	private boolean poderHeroicoUsado;
	private HashSet<Integer> lacaiosAtacaramID;
	private HashSet<Integer> lacaiosBaixados;

	@Override
	public int executarPartida() throws LamaException {
		vidaHeroi1 = vidaHeroi2 = 30;
		manaJogador1 = manaJogador2 = 1;
		nCartasHeroi1 = cartasIniJogador1; 
		nCartasHeroi2 = cartasIniJogador2;
		ArrayList<Jogada> movimentos = new ArrayList<Jogada>();
		int noCardDmgCounter1 = 1;
		int noCardDmgCounter2 = 1;
		turno = 1;
		
		for(int k = 0; k < 60; k++){
			imprimir("\n=== TURNO "+turno+" ===\n");
			// Atualiza mesa (com cópia profunda)
			@SuppressWarnings("unchecked")
			ArrayList<CartaLacaio> lacaios1clone = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(lacaiosMesa1);
			@SuppressWarnings("unchecked")
			ArrayList<CartaLacaio> lacaios2clone = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(lacaiosMesa2);
			mesa = new Mesa(lacaios1clone, lacaios2clone, vidaHeroi1, vidaHeroi2, nCartasHeroi1+1, nCartasHeroi2, turno>10?10:turno, turno>10?10:(turno==1?2:turno));

			// Apontadores para jogador1
			mao = maoJogador1;
			manaJogador1 = mesa.getManaJog1();
			lacaios = lacaiosMesa1;
			lacaiosOponente = lacaiosMesa2;
			jogador = 1;

			// Processa o turno 1 do Jogador1
			imprimir("\n----------------------- Começo de turno para Jogador 1:");
			long startTime, endTime, totalTime;
			
			// Cópia profunda de jogadas realizadas.
			@SuppressWarnings("unchecked")
			ArrayList<Jogada> cloneMovimentos1 = (ArrayList<Jogada>) UnoptimizedDeepCopy.copy(movimentos);
			
			startTime = System.nanoTime();
			if(baralho1.getCartas().size() > 0) {
				if(nCartasHeroi1 >= maxCartasMao){
				    /* Constroi as jogadas para o jogador 1 com base no processador de turno*/
					movimentos = jogador1.processarTurno(mesa, null, cloneMovimentos1);
					comprarCarta(); // carta descartada
				}
				else {
					movimentos = jogador1.processarTurno(mesa, comprarCarta(), cloneMovimentos1);
				}
			}
			else {
				imprimir("Fadiga: O Herói 1 recebeu "+noCardDmgCounter1+" de dano por falta de cartas no baralho. (Vida restante: "+(vidaHeroi1-noCardDmgCounter1)+").");
				vidaHeroi1 -= noCardDmgCounter1++;
				if( vidaHeroi1 <= 0){
					// Jogador 2 venceu
					imprimir("O jogador 2 venceu porque o jogador 1 recebeu um dano mortal por falta de cartas ! (Dano : " +(noCardDmgCounter1-1)+ ", Vida Herói 1: "+vidaHeroi1+")");
					return 2;
				}
				movimentos = jogador1.processarTurno(mesa, null, cloneMovimentos1);
			}
			endTime = System.nanoTime();
			totalTime = endTime - startTime;

			if( tempoLimitado!=0 && totalTime > 3e8){ // 300ms
				// Jogador 2 venceu, Jogador 1 excedeu limite de tempo
				return 2;
			}
			else {
				imprimir("Tempo usado em processarTurno(): " + totalTime / 1e6 + "ms");
			}

			// Começa a processar jogadas do Jogador 1
			this.poderHeroicoUsado = false;
            this.lacaiosAtacaramID = new HashSet<Integer>();
			this.lacaiosBaixados = new HashSet<Integer>();

            /* Chama o metodo processarJogada para os movimentos do jogador 1 */
			int vidaEnemy = (jogador == 1? vidaHeroi2: vidaHeroi1);
			int myLife = (jogador == 1? vidaHeroi1: vidaHeroi2);

			if(verbose > 1) {
				imprimir("Vida do heroi " + jogador + ": [" + myLife + "].");
				if (lacaios.size() > 0) {
					imprimir("E os seus lacaios: ");
					for (Carta a : lacaios) {
						if (a instanceof CartaLacaio) {
							CartaLacaio b = (CartaLacaio) a;
							imprimir("Lacaio_id=" + b.getID() +
									" - Vida - [" + b.getVidaAtual() +
									"/" + b.getVidaMaxima() +
									"] - " +
									"Ataque - [" + b.getAtaque() + "] - " +
									"Mana - [" + b.getMana() + "].");
						}
					}
				} else {
					imprimir("Esse jogador nao possui lacaios na mesa.");
				}
				imprimir("Vida do heroi " + ((jogador == 1) ? "2" : "1") + ": [" + vidaEnemy + "].");
				if (lacaiosOponente.size() > 0) {
					imprimir("E os seus lacaios: ");
					for (Carta a : lacaiosOponente) {
						if (a instanceof CartaLacaio) {
							CartaLacaio b = (CartaLacaio) a;
							imprimir("Lacaio_id=" + b.getID() +
									" - Vida - [" + b.getVidaAtual() +
									"/" + b.getVidaMaxima() +
									"] - " +
									"Ataque - [" + b.getAtaque() + "] - " +
									"Mana - [" + b.getMana() + "].");
						}
					}
				} else {
					imprimir("Esse jogador nao possui lacaios na mesa.");
				}
			}
            for(int i = 0; i < movimentos.size(); i++) {
				processarJogada (movimentos.get(i));

				/* Testar a cada rodada se o heroi se matou ou matou o adversario */
				if(vidaHeroi1 <= 0) {
					return 2;
				} else if(vidaHeroi2 <= 0) {
					return 1;
				}
			}
			vidaEnemy = (jogador == 1? vidaHeroi2: vidaHeroi1);
			myLife = (jogador == 1? vidaHeroi1: vidaHeroi2);
			if(verbose > 1) {
				imprimir("Apos as jogadas do jogador " + jogador + ":");

				imprimir("Vida do heroi " + jogador + ": [" + myLife + "].");
				if (lacaios.size() > 0) {
					imprimir("E os seus lacaios: ");
					for (Carta a : lacaios) {
						if (a instanceof CartaLacaio) {
							CartaLacaio b = (CartaLacaio) a;
							imprimir("Lacaio_id=" + b.getID() +
									" - Vida - [" + b.getVidaAtual() +
									"/" + b.getVidaMaxima() +
									"] - " +
									"Ataque - [" + b.getAtaque() + "] - " +
									"Mana - [" + b.getMana() + "].");
						}
					}
				} else {
					imprimir("Esse jogador nao possui lacaios na mesa.");
				}

				imprimir("Vida do heroi " + ((jogador == 1) ? "2" : "1") + ": [" + vidaEnemy + "].");
				if (lacaiosOponente.size() > 0) {
					imprimir("E os seus lacaios: ");
					for (Carta a : lacaiosOponente) {
						if (a instanceof CartaLacaio) {
							CartaLacaio b = (CartaLacaio) a;
							imprimir("Lacaio_id=" + b.getID() +
									" - Vida - [" + b.getVidaAtual() +
									"/" + b.getVidaMaxima() +
									"] - " +
									"Ataque - [" + b.getAtaque() + "] - " +
									"Mana - [" + b.getMana() + "].");
						}
					}
				} else {
					imprimir("Esse jogador nao possui lacaios na mesa.");
				}
			}
			// Atualiza mesa (com cópia profunda)
			@SuppressWarnings("unchecked")
			ArrayList<CartaLacaio> lacaios1clone2 = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(lacaiosMesa1);
			@SuppressWarnings("unchecked")
			ArrayList<CartaLacaio> lacaios2clone2 = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(lacaiosMesa2);
			mesa = new Mesa(lacaios1clone2, lacaios2clone2, vidaHeroi1, vidaHeroi2, nCartasHeroi1, nCartasHeroi2+1, turno>10?10:turno, turno>10?10:(turno==1?2:turno));
			
			// Apontadores para jogador2
			mao = maoJogador2;
			manaJogador2 = mesa.getManaJog2();
			lacaios = lacaiosMesa2;
			lacaiosOponente = lacaiosMesa1;
			jogador = 2;
			
			// Processa o turno 1 do Jogador2
			imprimir("\n\n----------------------- Começo de turno para Jogador 2:");
			
			// Cópia profunda de jogadas realizadas.
			@SuppressWarnings("unchecked")
			ArrayList<Jogada> cloneMovimentos2 = (ArrayList<Jogada>) UnoptimizedDeepCopy.copy(movimentos);
			
			startTime = System.nanoTime();
			
			if(baralho2.getCartas().size() > 0) {
				if(nCartasHeroi2 >= maxCartasMao){
					movimentos = jogador2.processarTurno(mesa, null, cloneMovimentos2);
					comprarCarta(); // carta descartada
				}
				else {
					movimentos = jogador2.processarTurno(mesa, comprarCarta(), cloneMovimentos2);
				}
			}
			else {
				imprimir("Fadiga: O Herói 2 recebeu "+noCardDmgCounter2+" de dano por falta de cartas no baralho. (Vida restante: "+(vidaHeroi2-noCardDmgCounter2)+").");
				vidaHeroi2 -= noCardDmgCounter2++;
				if(vidaHeroi2 <= 0) {
					// Vitoria do jogador 1
					imprimir("O jogador 1 venceu porque o jogador 2 recebeu um dano mortal por falta de cartas ! (Dano : " +(noCardDmgCounter2-1)+ ", Vida Herói 2: "+vidaHeroi2 +")");
					return 1;
				}
				movimentos = jogador2.processarTurno(mesa, null, cloneMovimentos2);
			}
			
			endTime = System.nanoTime();
			totalTime = endTime - startTime;
			if(tempoLimitado!=0 && totalTime > 3e8) { // 300ms
				// Limite de tempo pelo jogador 2. Vitoria do jogador 1.
				return 1;
			}
			else {
				imprimir("Tempo usado em processarTurno(): " + totalTime / 1e6 + "ms");
			}

			this.poderHeroicoUsado = false;
            this.lacaiosAtacaramID = new HashSet<Integer>();
			this.lacaiosBaixados = new HashSet<Integer>();

            //Comeca a processar as jogadas do jogador 2
            vidaEnemy = (jogador == 1? vidaHeroi2: vidaHeroi1);
			myLife = (jogador == 1? vidaHeroi1: vidaHeroi2);

			if(verbose > 1) {
				imprimir("Vida do heroi " + jogador + ": [" + myLife + "].");
				if (lacaios.size() > 0) {
					imprimir("E os seus lacaios: ");
					for (Carta a : lacaios) {
						if (a instanceof CartaLacaio) {
							CartaLacaio b = (CartaLacaio) a;
							imprimir("Lacaio_id=" + b.getID() +
									" - Vida - [" + b.getVidaAtual() +
									"/" + b.getVidaMaxima() +
									"] - " +
									"Ataque - [" + b.getAtaque() + "] - " +
									"Mana - [" + b.getMana() + "].");
						}
					}
				} else {
					imprimir("Esse jogador nao possui lacaios na mesa.");
				}

				imprimir("Vida do heroi " + ((jogador == 1) ? "2" : "1") + ": [" + vidaEnemy + "].");
				if (lacaiosOponente.size() > 0) {
					imprimir("E os seus lacaios: ");
					for (Carta a : lacaiosOponente) {
						if (a instanceof CartaLacaio) {
							CartaLacaio b = (CartaLacaio) a;
							imprimir("Lacaio_id=" + b.getID() +
									" - Vida - [" + b.getVidaAtual() +
									"/" + b.getVidaMaxima() +
									"] - " +
									"Ataque - [" + b.getAtaque() + "] - " +
									"Mana - [" + b.getMana() + "].");
						}
					}
				} else {
					imprimir("Esse jogador nao possui lacaios na mesa.");
				}
			}

			for(int i = 0; i < movimentos.size(); i++){
				processarJogada (movimentos.get(i));

				if(vidaHeroi2 <= 0) {
					// Vitoria do jogador 1
					return 1;
				} else if(vidaHeroi1 <= 0) {
					// Vitoria do jogador 2
					return 2;
				}
			}

			vidaEnemy = (jogador == 1? vidaHeroi2: vidaHeroi1);
			myLife = (jogador == 1? vidaHeroi1: vidaHeroi2);

			if(verbose > 1) {
				imprimir("Apos as jogadas do jogador " + jogador + ":");
				imprimir("Vida do heroi " + jogador + ": [" + myLife + "].");
				if (lacaios.size() > 0) {
					imprimir("E os seus lacaios: ");
					for (Carta a : lacaios) {
						if (a instanceof CartaLacaio) {
							CartaLacaio b = (CartaLacaio) a;
							imprimir("Lacaio_id=" + b.getID() +
									" - Vida - [" + b.getVidaAtual() +
									"/" + b.getVidaMaxima() +
									"] - " +
									"Ataque - [" + b.getAtaque() + "] - " +
									"Mana - [" + b.getMana() + "].");
						}
					}
				} else {
					imprimir("Esse jogador nao possui lacaios na mesa.");
				}
				imprimir("Vida do heroi " + ((jogador == 1) ? "2" : "1") + ": [" + vidaEnemy + "].");
				if (lacaiosOponente.size() > 0) {
					imprimir("E os seus lacaios: ");
					for (Carta a : lacaiosOponente) {
						if (a instanceof CartaLacaio) {
							CartaLacaio b = (CartaLacaio) a;
							imprimir("Lacaio_id=" + b.getID() +
									" - Vida - [" + b.getVidaAtual() +
									"/" + b.getVidaMaxima() +
									"] - " +
									"Ataque - [" + b.getAtaque() + "] - " +
									"Mana - [" + b.getMana() + "].");
						}
					}
				} else {
					imprimir("Esse jogador nao possui lacaios na mesa.");
				}
			}
			turno++;
		}
		
		// Nunca vai chegar aqui dependendo do número de rodadas
		imprimir("Erro: A partida não pode ser determinada em mais de 60 rounds. Provavel BUG.");
		throw new LamaException(-1, null, "Erro desconhecido. Mais de 60 turnos sem termino do jogo.", 0);
	}

	/* processarJogada retorna 1 se o jogador tiver se matado com o poder heroico */
	protected void processarJogada(Jogada umaJogada) throws LamaException {

		int vidaEnemy = (jogador == 1? vidaHeroi2: vidaHeroi1);
		int myLife = (jogador == 1? vidaHeroi1: vidaHeroi2);

	    int lacaioID;
		int alvoID;
		String errMessage;

	    if(umaJogada.getCartaJogada() == null) {
	    	lacaioID = -1;
		} else {
			lacaioID = umaJogada.getCartaJogada().getID();
		}

		/* Verificando qual tipo de jogada*/
		switch(umaJogada.getTipo()) {

		case ATAQUE:

			/* Caso eu va atacar o heroi inimigo, atribuir ID = -1 para o id do alvo */
            if(umaJogada.getCartaAlvo() == null) {
				alvoID = -1;
			} else {
            	/* Caso contrario, atribui o id do alvo */
				alvoID = umaJogada.getCartaAlvo().getID();
				for(Carta a: lacaiosOponente) {
					if(a instanceof CartaLacaio) {
						CartaLacaio b = (CartaLacaio) a;
						if(b.getID() == alvoID) {
							umaJogada.setCartaAlvo(b);
						}
					}
				}
			}

			if(umaJogada.getCartaJogada() != null) {
            	for(Carta a: lacaios) {
            		if(a instanceof CartaLacaio) {
            			CartaLacaio b = (CartaLacaio) a;
            			if(b.getID() == umaJogada.getCartaJogada().getID()) {
            				umaJogada.setCartaJogada(b);
						}
					}
				}
			} else {
                if(jogador == 1) {
					errMessage = "Erro: Atacar com um lacaio invalido de origem de ataque!\n" +
							"ID Atacante invalido: Heroi 1";
				} else {
					errMessage = "Erro: Atacar com um lacaio invalido de origem de ataque!\n" +
							"ID Atacante invalido: Heroi 2";
				}
				imprimir(errMessage);
                throw new LamaException(5, umaJogada, errMessage, jogador == 1 ? 2 : 1);
			}

			/* Procura por lacaios inimigos com o efeito de PROVOCAR */
			if(provocarLigado) {
				for(Carta lac: lacaiosOponente) {
					CartaLacaio lacaio = (CartaLacaio) lac;
					if(lacaio.getEfeito() == TipoEfeito.PROVOCAR) {
					   if(lacaio.getID() != umaJogada.getCartaAlvo().getID()) {
					   		errMessage = "Erro: Existindo um lacaio com provocar, atacar outro alvo\n" +
							"ID do alvo invalido: " + umaJogada.getCartaAlvo().getID() +
							"ID do lacaio com provocar: " + lacaio.getID();
					   		imprimir(errMessage);
					   		throw new LamaException(13, umaJogada, errMessage, jogador==1? 2: 1);
					   }
					}
				}
			}

			if(ataqueDuploLigado) {
			    if(umaJogada.getCartaJogada() instanceof CartaLacaio) {
			    	CartaLacaio lac = (CartaLacaio)umaJogada.getCartaJogada();
					if(lac.getEfeito() == TipoEfeito.ATAQUE_DUPLO) {
					    if(lacaiosAtacaramID.contains(lac.getID())) {
                            lacaiosAtacaramID.remove(lac.getID());
							lac.setEfeito(TipoEfeito.NADA);
						}
					}
				}
			}

			/* Caso o lacaio adicionado para atacar tenha o efeito de investida, podemos
			 * retira-lo do HASH de lacaios baixados, possibilitando que ele ataque nessa rodada */
			if(investidaLigada) {
				if(umaJogada.getCartaJogada() instanceof CartaLacaio) {
					CartaLacaio lac = (CartaLacaio)umaJogada.getCartaJogada();
					if(lac.getEfeito() == TipoEfeito.INVESTIDA) {
					    if(lacaiosBaixados.contains(lac.getID())) {
							lacaiosBaixados.remove(lac.getID());
							lac.setEfeito(TipoEfeito.NADA);
						}
					}
				}
			}

            /* Caso eu tente atacar com um lacaio que acabou de ser baixado e ele nao possui
             * o efeito de INVESTIDA */
            if (lacaiosBaixados.contains(umaJogada.getCartaJogada().getID())){
                errMessage = "Erro: Atacar com um lacaio que foi baixado nesse turno\n!" +
                        " ID atacante: " + lacaioID;
                imprimir(errMessage);
                throw new LamaException(6, umaJogada, errMessage, jogador == 1 ? 2 : 1);
            }

			/* Imprimindo a situacao da jogada e quem tentara ser atacado e por quem */
			if(umaJogada.getCartaJogada() instanceof CartaLacaio) {
				CartaLacaio l = (CartaLacaio) umaJogada.getCartaJogada();
                if(alvoID == -1) {
                        imprimir("JOGADA: O lacaio_id=" + lacaioID + " atacou o heroi inimigo." +
                                " Vida do heroi inimigo: [" + vidaEnemy + " ---> "
                                        + (vidaEnemy - l.getAtaque()) + "].");
                }
				else {
                	if(umaJogada.getCartaAlvo() instanceof CartaLacaio) {
                		CartaLacaio al = (CartaLacaio) umaJogada.getCartaAlvo();
						imprimir("JOGADA: O lacaio_id=" + lacaioID + " atacou o lacaio_id=" + alvoID + "." +
								" Vida do lacaio inimigo: [" + al.getVidaAtual() + " ---> "
								+ (al.getVidaAtual() - l.getAtaque()) + "] - Vida do meu lacaio: [" +
								l.getVidaAtual() + " ---> " +
								(l.getVidaAtual() - al.getAtaque()) + "].");

					}
				}
			}

			/* Caso a carta a atacar nao tenha atacado ainda */
			if(!lacaiosAtacaramID.contains(umaJogada.getCartaJogada().getID())) {
				/* Caso a carta que vai atacar seja um lacaio da minha mesa */
				if (lacaios.contains(umaJogada.getCartaJogada())) {
					/* Caso eu va atacar um lacaio da mesa do inimigo*/
					if(lacaiosOponente.contains(umaJogada.getCartaAlvo())) {

						CartaLacaio jogada = (CartaLacaio) umaJogada.getCartaJogada();
						CartaLacaio alvo = (CartaLacaio) umaJogada.getCartaAlvo();

                        /* Adicionando o lacaio que ja atacou no hash de ataques */
						lacaiosAtacaramID.add(jogada.getID());

						for (int i = 0; i < lacaiosOponente.size(); i++) {
							CartaLacaio l = (CartaLacaio) lacaiosOponente.get(i);
							if (l.getID() == alvo.getID()) {
                                /* Diminuindo a vida do alvo */
								l.setVidaAtual(alvo.getVidaAtual() - jogada.getAtaque());
							}
							if (l.getVidaAtual() <= 0) {
                                /* Retirando o alvo caso ele tenha morrido */
								lacaiosOponente.remove(i);
							}
						}
						for (int i = 0; i < lacaios.size(); i++) {
							CartaLacaio l = (CartaLacaio) lacaios.get(i);
							if (l.getID() == jogada.getID()) {
                                /* Diminuindo a vida do alvo */
								l.setVidaAtual(jogada.getVidaAtual() - alvo.getAtaque());
							}
							if (l.getVidaAtual() <= 0) {
                                /* Retirando o alvo caso ele tenha morrido */
								lacaios.remove(i);
							}
						}

					}
					/* Caso eu va atacar o heroi inimigo */
					else if(umaJogada.getCartaAlvo() == null) {

                        /* Adicionando que o heroi atacou */
                        lacaiosAtacaramID.add(umaJogada.getCartaJogada().getID());

                        /* Verificando qual heroi esta jogando para retirar a vida do oponente*/
                        if(jogador == 1) {
                            vidaHeroi2 = vidaHeroi2 - ((CartaLacaio) umaJogada.getCartaJogada()).getAtaque();
                        } else {
                            vidaHeroi1 = vidaHeroi1 - ((CartaLacaio) umaJogada.getCartaJogada()).getAtaque();
                        }
					}
					/* Caso o meu alvo seja de qualquer outro tipo */
					else {
						errMessage = "Erro: Atacar com um lacaio com alvo invalido!\n" +
								"ID Atacante invalido: " + lacaioID + "\n" +
								"ID Alvo invalido: " + alvoID;
						imprimir(errMessage);
						throw new LamaException(8, umaJogada, errMessage, jogador == 1 ? 2 : 1);
					}
				}
				/* Caso a carta que vai atacar nao esteja entre os lacaios da minha mesa */
				else {
                    errMessage = "Erro: Atacar com um lacaio invalido de origem de ataque!\n" +
                            "ID Atacante invalido: " + lacaioID;
                    imprimir(errMessage);
                    throw new LamaException(5, umaJogada, errMessage, jogador == 1 ? 2 : 1);
				}
			}

			/* Tratando o caso de um lacaio que tentar atacar novamente, verificando
			*  se o seu id ja esta numa tabela HASH */
			else {
				errMessage = "Erro: Atacar com um lacaio mais de uma vez por turno!\n" +
						"ID atacante: " + lacaioID;
				imprimir(errMessage);
				throw new LamaException(7, umaJogada, errMessage, jogador == 1? 2 : 1);
			}
			break;
		case LACAIO:
            CartaLacaio lac;

            if(lacaios.size() >= 7) {
            	errMessage = "Erro: Baixar um lacaio ja possuindo outros sete lacaios na mesa\n" +
            	"ID: " + lacaioID + "\n" +
				"Nome da carta: " + umaJogada.getCartaJogada().getNome();
            	imprimir(errMessage);
            	throw new LamaException(4, umaJogada, errMessage, jogador==1? 2: 1);
			}
			if(umaJogada.getCartaJogada() instanceof CartaLacaio) {
				lac = (CartaLacaio)umaJogada.getCartaJogada();
                /* Imprimindo a tentativa de jogada */
				imprimir("JOGADA: O lacaio_id="+lacaioID+" foi baixado para a mesa." +
						" Nome: [" +lac.getNome() + "] -" +
						" Vida: [" + lac.getVidaAtual() +"/" + lac.getVidaMaxima()+ "] -" +
						" Ataque: [" + lac.getAtaque() + "] -" +
						" Custo de Mana: [" + lac.getMana() +"].");
			} else {
				errMessage = "Erro: Tentar baixar na mesa uma carta de magia como carta lacaio\n" +
				"ID: " + lacaioID + "\n" +
				"Nome da carta: " + umaJogada.getCartaJogada().getNome();
				imprimir(errMessage);
				throw new LamaException(3, umaJogada, errMessage, jogador==1? 2:1);
			}

			if(jogador == 1) {
				if (umaJogada.getCartaJogada().getMana() > manaJogador1) {
				    errMessage = "Erro: Realizar uma jogada que o limite de mana nao permite!\n" +
							"Tipo da jogada: " + umaJogada.getTipo() + "\n" +
							"Custo de mana: " + umaJogada.getCartaJogada().getMana() + "\n" +
							"Mana disponivel: " + manaJogador1;
			    	imprimir(errMessage);
			    	throw new LamaException(2, umaJogada, errMessage, 2);
				}
			} else {
				if (umaJogada.getCartaJogada().getMana() > manaJogador2) {
				    errMessage = "Erro: Realizar uma jogada que o limite de mana nao permite!\n" +
							"Tipo da jogada: " + umaJogada.getTipo() + "\n" +
							"Custo de mana: " + umaJogada.getCartaJogada().getMana() + "\n" +
							"Mana disponivel: " + manaJogador2;
			    	imprimir(errMessage);
			    	throw new LamaException(2, umaJogada, errMessage, 1);
				}
			}
			/* Caso eu tenha a carta que eu vou tentar jogar na minha mao */
			if(mao.contains(umaJogada.getCartaJogada())){
				Carta lacaioBaixado = mao.get(mao.indexOf(umaJogada.getCartaJogada()));
				lacaios.add(lacaioBaixado); // lacaio adicionado à mesa
				mao.remove(umaJogada.getCartaJogada()); // lacaio retirado da mão

				/* Adicionando o lacaio baixado ao hash de lacaios baixados */
                lacaiosBaixados.add(lacaioBaixado.getID());
			}

			/* Caso eu tente jogar uma carta que nao esteja na minha mao */
			else {
				errMessage = "Erro: Tentou-se usar a carta_id="+lacaioID+
						", porém esta carta não existe na mao. IDs de cartas na mao: ";
				for(Carta card : mao){
					errMessage += card.getID() + ", ";
				}
				imprimir(errMessage);
				// Dispara uma LamaException passando o código do erro, uma mensagem descritiva correspondente e qual jogador deve vencer a partida.
				throw new LamaException(1, umaJogada, errMessage, jogador==1?2:1);
			}
			// Obs: repare que este código funcionará tanto quando trata-se do turno do jogador1 ou do jogador2.
			break;
		case MAGIA:

			/* Caso eu va atacar o heroi inimigo, atribuir ID = -1 para o id do alvo */
            if(umaJogada.getCartaAlvo() == null) {
				alvoID = -1;
			} else {
            	/* Caso contrario, atribui o id do alvo */
				alvoID = umaJogada.getCartaAlvo().getID();
				for(Carta a: lacaiosOponente) {
					if(a instanceof CartaLacaio) {
						CartaLacaio b = (CartaLacaio) a;
						if(b.getID() == alvoID) {
							umaJogada.setCartaAlvo(b);
						}
					}
				}
			}

			if(umaJogada.getCartaJogada() instanceof CartaMagia) {
				CartaMagia mag = (CartaMagia)umaJogada.getCartaJogada();

				/* Impressao da jogada de ataque de magia */
				if(mag.getMagiaTipo() == TipoMagia.ALVO) {
					if (umaJogada.getCartaAlvo() == null) {
						imprimir("JOGADA: A magia_id=" + lacaioID + " atacou o alvo heroi inimigo." +
								" Vida do heroi inimigo: [" + vidaEnemy + " ---> "
								+ (vidaEnemy - mag.getMagiaDano())
								+ "].");
					} else {
						if (umaJogada.getCartaAlvo() instanceof CartaLacaio) {
							CartaLacaio alvo = (CartaLacaio) umaJogada.getCartaAlvo();
							imprimir("JOGADA: A magia_id=" + lacaioID + " atacou o alvo lacaio_id="
									+ alvo.getID() + "." +
									" Vida do lacaio inimigo: [" + (alvo.getVidaAtual() +
									mag.getMagiaDano() )+ " ---> "
									+ alvo.getVidaAtual() + "].");
						}
					}
				} else if (mag.getMagiaTipo() == TipoMagia.AREA) {

					imprimir("JOGADA: A magia_id=" +lacaioID +" de area atacou os lacaios e o heroi inimigo.");
					for(Carta l: lacaiosOponente) {
						CartaLacaio la = (CartaLacaio) l;
							String a = (la.getID() < 10) ? " " : "";
							imprimir("Vida lacaio_id=" +a +la.getID()+ ": ["
									+ la.getVidaAtual() + " ---> "
									+ (la.getVidaAtual() - mag.getMagiaDano()) + "].");
					}
					imprimir("Vida do heroi inimigo: [" + vidaEnemy + " ---> "
							+ (vidaEnemy - mag.getMagiaDano()) +"].");
				} else {
					if(umaJogada.getCartaAlvo() instanceof CartaLacaio) {
						CartaLacaio l = (CartaLacaio)umaJogada.getCartaAlvo();
						imprimir("JOGADA: a magia_id=" + lacaioID + " buffou o lacaio_id=" + l.getID()+ "." +
								" Vida do lacaio: [" + l.getVidaAtual() + " ---> " +
								(l.getVidaAtual() + mag.getMagiaDano()) + "] - " +
								"Ataque do lacaio: [" + l.getAtaque() + " ---> " +
								(l.getAtaque() + mag.getMagiaDano()) + "].");
					}
				}
			}

			if (umaJogada.getCartaJogada() instanceof CartaLacaio) {
				errMessage = "Erro: Tentar usar uma carta Lacaio como uma magia!\n" +
						"ID da carta usada: " + umaJogada.getCartaJogada().getID() + "\n" +
						"Nome da carta: " + umaJogada.getCartaJogada().getNome();
				imprimir(errMessage);
				throw new LamaException(9, umaJogada, errMessage, jogador == 1 ? 2 : 1);
			}

			if(!mao.contains(umaJogada.getCartaJogada())) {
					errMessage = "Erro: Tentou-se usar a carta_id="+lacaioID+"," +
							" porém esta carta não existe na mao. IDs de cartas na mao: ";
					for(Carta card : mao){
						errMessage += card.getID() + ", ";
					}
					imprimir(errMessage);
					// Dispara uma LamaException passando o código do erro, uma mensagem
					// descritiva correspondente e qual jogador deve vencer a partida.
					throw new LamaException(1, umaJogada, errMessage, jogador==1?2:1);
            }

			if(jogador == 1) {
			    if(manaJogador1 >= umaJogada.getCartaJogada().getMana()) {
                    CartaMagia magia = (CartaMagia) umaJogada.getCartaJogada();
                    Carta alvo = umaJogada.getCartaAlvo();
					manaJogador1 -= magia.getMana();

					/* Se a magia de ataque for de area */
                    if (magia.getMagiaTipo() == TipoMagia.AREA) {

                    	mao.remove(magia);

                    	/* Diminui a vida do heroi inimigo */
						vidaHeroi2 -= magia.getMagiaDano();

						/* Cria um ArrayList com os lacaios a serem removidos */
						ArrayList<CartaLacaio> index = new ArrayList<>();
						for(Carta l : lacaiosOponente) {
							((CartaLacaio) l).setVidaAtual(((CartaLacaio)l).getVidaAtual() - magia.getMagiaDano());
							/* Adicionando os lacaios a serem removidos */
							if(((CartaLacaio) l).getVidaAtual() <= 0) {
								index.add((CartaLacaio)l);
							}
						}

						/* Removendo os lacaios adicionados ao 'index' */
						for(int k = 0 ; k < index.size() ; k++) {
							lacaiosOponente.remove(index.get(k));
						}


                    } else if (magia.getMagiaTipo() == TipoMagia.BUFF) {
                        if (lacaios.contains(alvo)) {
							((CartaLacaio) alvo).setVidaAtual(((CartaLacaio) alvo).getVidaAtual() +
								magia.getMagiaDano());
							((CartaLacaio) alvo).setAtaque(((CartaLacaio) alvo).getAtaque()
									+ magia.getMagiaDano());

							/* Aumentando o ATAQUE e a VIDA do lacaio buffado */
							for(Carta l : lacaios) {
								if(l.getID() == alvo.getID()) {
									((CartaLacaio) l).setAtaque(((CartaLacaio)alvo).getAtaque());
									((CartaLacaio) l).setVidaAtual(((CartaLacaio)alvo).getVidaAtual());
								}
							}
							mao.remove(magia);
                        } else {
                            if(alvoID == -1) {
									errMessage = "Usar uma magia de buff em um alvo invalido!\n" +
											"ID do buff: " + magia.getID() + "\n" +
											"ID invalido do alvo: Heroi 2";
							} else {
                            	errMessage = "Usar uma magia de buff em um alvo invalido!\n" +
									"ID do buff: " + magia.getID() + "\n" +
									"ID invalido do alvo: " + alvoID;
							}
							imprimir(errMessage);
							throw new LamaException(10, umaJogada, errMessage, 2);
						}
                    } else if (magia.getMagiaTipo() == TipoMagia.ALVO) {
						mao.remove(magia);
                        if (alvo == null) {
							vidaHeroi2 -= magia.getMagiaDano();
                        } else if (lacaiosOponente.contains(alvo)) {
                        	for(int i = 0; i < lacaiosOponente.size(); i++) {
                                CartaLacaio l = (CartaLacaio) lacaiosOponente.get(i);
                                if (l.getID() == alvo.getID()) {
                                	/* Diminuindo a vida do alvo */
                                    l.setVidaAtual(((CartaLacaio)alvo).getVidaAtual() - magia.getMagiaDano());
                                }
                                if(l.getVidaAtual() <= 0) {
                                	/* Retirando o alvo caso ele tenha morrido */
									lacaiosOponente.remove(i);
								}
                            }
                        } else {
                        	if(alvoID == -1) {
									errMessage = "Usar uma magia de alvo em um alvo invalido!\n" +
											"ID do buff: " + magia.getID() + "\n" +
											"ID invalido do alvo: Heroi 2";
							} else {
                            	errMessage = "Usar uma magia de alvo em um alvo invalido!\n" +
									"ID do buff: " + magia.getID() + "\n" +
									"ID invalido do alvo: " + alvoID;
							}
							imprimir(errMessage);
							throw new LamaException(10, umaJogada, errMessage, 2);
						}
                    }
				} else {
			    	errMessage = "Erro: Realizar uma jogada que o limite de mana nao permite!\n" +
							"Tipo da jogada: " + umaJogada.getTipo() + "\n" +
							"Custo de mana: " + umaJogada.getCartaJogada().getMana() + "\n" +
							"Mana disponivel: " + manaJogador1;
			    	imprimir(errMessage);
			    	throw new LamaException(2, umaJogada, errMessage, 2);
				}
			} else if(jogador == 2) {
				if(manaJogador2 >= umaJogada.getCartaJogada().getMana()) {
                    CartaMagia magia = (CartaMagia) umaJogada.getCartaJogada();
                    Carta alvo = umaJogada.getCartaAlvo();
					manaJogador2 -= magia.getMana();

                    if (magia.getMagiaTipo() == TipoMagia.AREA) {
                    	mao.remove(magia);
						vidaHeroi1 -= magia.getMagiaDano();

						ArrayList<CartaLacaio> index = new ArrayList<>();
						for(Carta l : lacaiosOponente) {
							((CartaLacaio) l).setVidaAtual(((CartaLacaio)l).getVidaAtual() - magia.getMagiaDano());
							if(((CartaLacaio) l).getVidaAtual() <= 0) {
								index.add((CartaLacaio)l);
							}
						}

						for(int k = 0 ; k < index.size(); k++) {
							lacaiosOponente.remove(index.get(k));
						}
                    } else if (magia.getMagiaTipo() == TipoMagia.BUFF) {
                        if (lacaios.contains(alvo)) {
							((CartaLacaio) alvo).setVidaAtual(((CartaLacaio) alvo).getVidaAtual() +
								magia.getMagiaDano());
							((CartaLacaio) alvo).setAtaque(((CartaLacaio) alvo).getAtaque()
									+ magia.getMagiaDano());

							for(Carta l : lacaios) {
								if(l.getID() == alvo.getID()) {
									((CartaLacaio) l).setAtaque(((CartaLacaio)alvo).getAtaque());
									((CartaLacaio) l).setVidaAtual(((CartaLacaio)alvo).getVidaAtual());
								}
							}
							mao.remove(magia);
                        } else {
                            if(alvoID == -1) {
									errMessage = "Usar uma magia de buff em um alvo invalido!\n" +
											"ID do buff: " + magia.getID() + "\n" +
											"ID invalido do alvo: Heroi 1";
							} else {
                            	errMessage = "Usar uma magia de buff em um alvo invalido!\n" +
									"ID do buff: " + magia.getID() + "\n" +
									"ID invalido do alvo: " + alvoID;
							}
							imprimir(errMessage);
							throw new LamaException(10, umaJogada, errMessage, 1);
						}
                    } else if (magia.getMagiaTipo() == TipoMagia.ALVO) {
						mao.remove(magia);
                        if (alvo == null) {
							vidaHeroi1 -= magia.getMagiaDano();
                        } else if (lacaiosOponente.contains(alvo)) {
                            for(int i = 0; i < lacaiosOponente.size(); i++) {
                                CartaLacaio l = (CartaLacaio) lacaiosOponente.get(i);
                                if (l.getID() == alvo.getID()) {
                                	/* Diminuindo a vida do alvo */
                                    l.setVidaAtual(((CartaLacaio)alvo).getVidaAtual() - magia.getMagiaDano());
                                }
                                if(l.getVidaAtual() <= 0) {
                                	/* Retirando o alvo caso ele tenha morrido */
									lacaiosOponente.remove(i);
								}
                            }
                        } else {
                            if(alvoID == -1) {
									errMessage = "Usar uma magia de alvo em um alvo invalido!\n" +
											"ID do buff: " + magia.getID() + "\n" +
											"ID invalido do alvo: Heroi 1";
							} else {
                            	errMessage = "Usar uma magia de alvo em um alvo invalido!\n" +
									"ID do buff: " + magia.getID() + "\n" +
									"ID invalido do alvo: " + alvoID;
							}
							imprimir(errMessage);
							throw new LamaException(10, umaJogada, errMessage, 1);
						}
                    }
				} else {
			    	errMessage = "Erro: Realizar uma jogada que o limite de mana nao permite!\n" +
							"Tipo da jogada: " + umaJogada.getTipo() + "\n" +
							"Custo de mana: " + umaJogada.getCartaJogada().getMana() + "\n" +
							"Mana disponivel: " + manaJogador2;
			    	imprimir(errMessage);
			    	throw new LamaException(2, umaJogada, errMessage, 1);
				}
			}
			break;
		case PODER:

			if(umaJogada.getCartaAlvo() != null) {
				alvoID = umaJogada.getCartaAlvo().getID();
				for(Carta a: lacaiosOponente) {
					if(a instanceof CartaLacaio) {
						CartaLacaio b = (CartaLacaio) a;
						if(b.getID() == alvoID) {
							/* Atualizando o alvo, para o caso de multiplos ataques */
							umaJogada.setCartaAlvo(b);
						}
					}
				}
			} else {
				alvoID = -1;
			}

			if(provocarLigado) {
				for(Carta laca: lacaiosOponente) {
					CartaLacaio lacaio = (CartaLacaio) laca;
					if(lacaio.getEfeito() == TipoEfeito.PROVOCAR) {
                        alvoID = lacaio.getID();
						umaJogada.setCartaAlvo(lacaio);
					}
				}
			}

			if(umaJogada.getCartaAlvo() == null) {
				imprimir("JOGADA: poder heroico foi usado no heroi inimigo." +
                        " Vida do heroi inimigo: [" + vidaEnemy + " ---> "
						+ (vidaEnemy - 1) + "].");
			} else {
				if(umaJogada.getCartaAlvo() instanceof CartaLacaio) {
				    CartaLacaio l = (CartaLacaio)umaJogada.getCartaAlvo();
					imprimir("JOGADA: poder heroico foi usado em lacaio_id=" + alvoID + "." +
							" Vida do lacaio inimigo: [" + l.getVidaAtual() +
							" ---> " + (l.getVidaAtual() -1) + "] - Vida do meu heroi: [" +
							(myLife) + " ---> " + (myLife -l.getAtaque()) + "].");
				}
			}

			if(poderHeroicoUsado) {
				if(alvoID == -1) {
					if(jogador == 1) {
						errMessage = "Erro: Usar o poder heroico mais de uma vez por turno!\n" +
								"ID do alvo: Heroi 2";
					} else {
						errMessage = "Erro: Usar o poder heroico mais de uma vez por turno!\n" +
								"ID do alvo: Heroi 1";
					}
				} else {
					errMessage = "Erro: Usar o poder heroico mais de uma vez por turno!\n" +
							"ID do alvo: " + alvoID;
				}
				imprimir(errMessage);
				throw new LamaException(11, umaJogada, errMessage, jogador==1?2:1);
			}

			if(jogador == 1) {
				if(manaJogador1 >= 2) {
                    if (umaJogada.getCartaAlvo() == null) {
                    	/* Diminui a vida do heroi inimigo */
                        vidaHeroi2 -= 1;
                    } else {
                        if (lacaiosOponente.contains(umaJogada.getCartaAlvo())) {
                            CartaLacaio alvo = (CartaLacaio) umaJogada.getCartaAlvo();

                            /* Diminui a vida do meu heroi */
							vidaHeroi1 -= alvo.getAtaque();

                            for(int i = 0; i < lacaiosOponente.size(); i++) {
                                CartaLacaio l = (CartaLacaio) lacaiosOponente.get(i);
                                if (l.getID() == alvo.getID()) {
                                	/* Diminuindo a vida do alvo */
                                    l.setVidaAtual(alvo.getVidaAtual() - 1);
                                }
                                /* Removendo caso o alvo tenha morrido */
                                if(l.getVidaAtual() <= 0) {
									lacaiosOponente.remove(i);
								}
                            }
                        } else {
                            errMessage = "Erro: Usar o poder heroico em um alvo invalido!\n" +
									"ID do alvo invalido: " + umaJogada.getCartaAlvo().getID();
                            imprimir(errMessage);
                            throw new LamaException(12, umaJogada, errMessage, 2);
                        }
                    }

					poderHeroicoUsado = true;
				} else {
					errMessage = "Erro: Realizar uma jogada que o limite de mana nao permite!\n" +
							"Tipo de jogada: " + umaJogada.getTipo() + "\n" +
							"Custo de Mana: 2\n" +
							"Mana disponivel: " + manaJogador1;
					imprimir(errMessage);
					throw new LamaException(2, umaJogada, errMessage, 2);
				}
			} else if(jogador == 2) {
				if(manaJogador2 >= 2) {
                    if (umaJogada.getCartaAlvo() == null) {
						/* Diminuindo a vida do heroi inimigo*/
						vidaHeroi1 -= 1;
                    } else {
                        if (lacaiosOponente.contains(umaJogada.getCartaAlvo())) {
                            CartaLacaio alvo = (CartaLacaio) umaJogada.getCartaAlvo();

                            /* Diminuindo a vida do meu heroi */
                           	vidaHeroi2 -= alvo.getAtaque();

                           	for(int i = 0; i < lacaiosOponente.size(); i++) {
                                CartaLacaio l = (CartaLacaio) lacaiosOponente.get(i);
                                if (l.getID() == alvo.getID()) {
                                	/* Diminuindo a vida do alvo */
                                    l.setVidaAtual(alvo.getVidaAtual() - 1);
                                }

                                /* Retirando o alvo caso ele tenha morrido */
                                if(l.getVidaAtual() <= 0) {
									lacaiosOponente.remove(i);
								}
                            }
                        } else {
                            errMessage = "Erro: Usar o poder heroico em um alvo invalido!\n" +
									"ID do alvo invalido: " + umaJogada.getCartaAlvo().getID();
                            imprimir(errMessage);
                            throw new LamaException(12, umaJogada, errMessage, 1);
                        }
                    }
					poderHeroicoUsado = true;
				} else {
					errMessage = "Erro: Realizar uma jogada que o limite de mana nao permite!\n" +
							"Tipo de jogada: " + umaJogada.getTipo() + "\n" +
							"Custo de Mana: 2\n" +
							"Mana disponivel: " + manaJogador2;
					imprimir(errMessage);
					throw new LamaException(2, umaJogada, errMessage, jogador==1?2:1);
				}
			}
			break;
		default:
			break;
		}
	}
	
	private Carta comprarCarta() {
		if(this.jogador == 1) {
			if(baralho1.getCartas().size() <= 0)
				throw new RuntimeException("Erro: Não há mais cartas no baralho para serem compradas.");
			Carta nova = baralho1.comprarCarta();
			mao.add(nova);
			nCartasHeroi1++;
			return (Carta) UnoptimizedDeepCopy.copy(nova);
		}
		else {
			if(baralho2.getCartas().size() <= 0)
				throw new RuntimeException("Erro: Não há mais cartas no baralho para serem compradas.");
			Carta nova = baralho2.comprarCarta();
			mao.add(nova);
			nCartasHeroi2++;
			return (Carta) UnoptimizedDeepCopy.copy(nova);
		}
	}
}
