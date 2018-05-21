/*
* Autor: Gabriel Pellegrino da Silva
* Ra: 172358
*
* O codigo abaixo trata o processamento de jogadas, atualizacao da vida dos lacaios e dos herois.
* Uma particularidade desse codigo eh que se a verbosidade for mudada para 2 na Main, teremos
* um acompanhamento muito mais detalhado do estado do jogo. O que tambem pode auxiliar na depuracao
* durante as etapas de desenvolvimento.
*
* Sao recebidos uma estrutura de jogada que contem um desejo do jogador. A necessidade do motor eh validar
* o desejo de jogada do jogador e efetuar a jogada caso possivel, e caso nao seja possivel, enviar uma aviso
* com informacoes pertinentes ao erro e o contexto do erro.
*
* Um processo importante eh nao confiar na jogada que o jogador passsou para o motor. O motor deve
* recuperar os ID's que foram passados e recuperar as cartas 'verdadeiras' da mao do jogador ou da
* mesa.
*
* Durante a depuracao do codigo, uma ferramenta muito importante é mostrar ao desenvolvedor o estado
* da partida, como estao as cartas na mesa e na mao, como o processo de jogo esta evoluindo, e se as
* mudancas estao de acordo com as jogadas realizadas, para isso foi criada a verbosidade 2 no metodo
* imprimir; a qual imprime informacoes adicionais sobre a situacao atual da mesa e da vida dos herois
* antes e depois das jogadas.
*
* Uma das maiores dificuldades de construcao deste codigo esta na percepcao e teste de todas as possi-
* bilidades de erros que um jogador pode cometer. No trabalho 1, fizemos o outro lado, o jogador ten-
* tando destruir o oponente. Agora, foi desenvolvido como um 'bot' trataria os pedidos de um jogador.
*
* Para testar as jogadas invalidas, foi utilizado o jogador do trabalho 1, e também criado alguns joga-
* dores de teste. Bem como lançar 500.000 vezes os jogadores aleatorios fornecidos para degladiar.
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
	private int nCartasHeroi1;
	private int nCartasHeroi2;
	private int prov;
	private int mana;
	private int vidaHeroiInimigo;
	// "Apontadores" - Assim podemos programar genericamente os métodos para funcionar
	// com ambos os jogadores
	private ArrayList<Carta> mao;
	private ArrayList<Carta> lacaios;
	private ArrayList<Carta> lacaiosOponente;
	
	// "Memória" - Para marcar ações que só podem ser realizadas uma vez por turno.
	private boolean poderHeroicoUsado;
	private HashSet<Integer> lacaiosAtacaramID;
	private HashSet<Integer> lacaiosBaixados;
	private HashSet<Integer> ataqueDuploUsado;

	/* O metodo provocar retorna
	*  false: se o ID do alvo atual possui provocar
	*  true: caso o ID do alvo atual nao possua provocar e outro lacaio inimigo tenha a provocar*/
	private boolean provocar(int alvoID) {
		if(provocarLigado) {
			/* Primeiro procuramos se o alvo tem provocar */
			for(Carta lac : lacaiosOponente) {
				CartaLacaio lacaio = (CartaLacaio) lac;
				if (lacaio.getEfeito() == TipoEfeito.PROVOCAR) {
					if (lacaio.getID() == alvoID) {
						return false;
					}
				}
			}
			/* Se o alvo nao tem provocar, verificamos se algum outro lacaio inimigo possui */
			for (Carta lac : lacaiosOponente) {
				CartaLacaio lacaio = (CartaLacaio) lac;
				if (lacaio.getEfeito() == TipoEfeito.PROVOCAR) {
					if (lacaio.getID() != alvoID) {
						prov = lacaio.getID();
						return true;
					}
				}
			}
		}
		return false;
	}

	/* Atualizacao da vida pos ataques e remocao dos lacaios que morreram */
	private void atualizaLacs(ArrayList<Carta> cartas, CartaLacaio jogada, CartaLacaio alvo) {
		for (int i = 0; i < cartas.size(); i++) {
            CartaLacaio l = (CartaLacaio) cartas.get(i);
            if (l.getID() == jogada.getID()) {
                /* Diminuindo a vida do alvo */
                l.setVidaAtual(jogada.getVidaAtual() - alvo.getAtaque());
            }
            if (l.getVidaAtual() <= 0) {
                /* Retirando o alvo caso ele tenha morrido */
                cartas.remove(i);
               	i--;
            }
        }
	}

	/* Percorre os lacaios inimigos e coloca em umaJogada a carta da mesa
	*  no lugar da carta que foi passada, evitando fraudes */
	private int setAlvoID(Jogada umaJogada) {
		if(umaJogada.getCartaAlvo() == null) {
		    	return -1;
			} else {
            	/* Caso contrario, atribui o id do alvo */
				int alvoID = umaJogada.getCartaAlvo().getID();
				for(Carta a: lacaiosOponente) {
                    if(a.getID() == alvoID) {
                        umaJogada.setCartaAlvo(a);
                    }
				}
				return alvoID;
			}

	}

	/* Apenas imprime as propriedades dos lacaios de um jogador */
	private void printLacs(ArrayList<Carta> lacaios) {
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
							"Mana - [" + b.getMana() + "] - " +
							"Efeito - [" + b.getEfeito() + "].");
                    }
                }
		} else {
			imprimir("Esse jogador nao possui lacaios na mesa.");
		}
	}

	/* Impressao do estado atual
	*  boolean played: serve apenas para imprimir o estado antes e depois da jogada de maneira diferente */
	private void printState(boolean played) {
		int vidaEnemy = (jogador == 1? vidaHeroi2: vidaHeroi1);
		int minhaVida = (jogador == 1? vidaHeroi1: vidaHeroi2);
        if(verbose > 1) {
        	if(played) {
				imprimir("Apos as jogadas do jogador " + jogador + ":");
			}

            imprimir("Vida do heroi " + jogador + ": [" + minhaVida + "].");
			printLacs(lacaios);
			imprimir("Vida do heroi " + ((jogador == 1) ? "2" : "1") + ": [" + vidaEnemy + "].");
			printLacs(lacaiosOponente);

            if(!played) {
				imprimir("Jogadas do jogador " + jogador + ":");
			}
        }
	}
	@Override
	public int executarPartida() throws LamaException {
		vidaHeroi1 = vidaHeroi2 = 30;
		manaJogador1 = manaJogador2 = 1;
		nCartasHeroi1 = cartasIniJogador1; 
		nCartasHeroi2 = cartasIniJogador2;
		ArrayList<Jogada> movimentos = new ArrayList<>();
		int noCardDmgCounter1 = 1;
		int noCardDmgCounter2 = 1;
		int turno = 1;
		
		for(int k = 0; k < 60; k++){
			imprimir("\n=== TURNO "+turno+" ===\n");
			// Atualiza mesa (com cópia profunda)
			@SuppressWarnings("unchecked")
			ArrayList<CartaLacaio> lacaios1clone = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(lacaiosMesa1);
			@SuppressWarnings("unchecked")
			ArrayList<CartaLacaio> lacaios2clone = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(lacaiosMesa2);
			Mesa mesa = new Mesa(lacaios1clone, lacaios2clone, vidaHeroi1, vidaHeroi2, nCartasHeroi1+1, nCartasHeroi2, turno>10?10:turno, turno>10?10:(turno==1?2:turno));

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
            this.lacaiosAtacaramID = new HashSet<>();
			this.lacaiosBaixados = new HashSet<>();
			this.ataqueDuploUsado = new HashSet<>();

			printState(false);

			if(movimentos.size() == 0) {
				imprimir("Nenhuma jogada");
			}
            for(Jogada i: movimentos) {
				processarJogada (i);

				/* Testar a cada rodada se o heroi se matou ou matou o adversario */
				if(vidaHeroi1 <= 0) {
					printState(true);
					return 2;
				} else if(vidaHeroi2 <= 0) {
					printState(true);
					return 1;
				}
			}

			printState(true);

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
            this.lacaiosAtacaramID = new HashSet<>();
			this.lacaiosBaixados = new HashSet<>();
			this.ataqueDuploUsado = new HashSet<>();

			printState(false);

			if(movimentos.size() == 0) {
				imprimir("Nenhuma jogada");
			}

			for(Jogada i: movimentos){
				processarJogada (i);

				if(vidaHeroi2 <= 0) {
					// Vitoria do jogador 1
					printState(true);
					return 1;
				} else if(vidaHeroi1 <= 0) {
					// Vitoria do jogador 2
					printState(true);
					return 2;
				}
			}

			printState(true);
			turno++;
		}
		
		// Nunca vai chegar aqui dependendo do número de rodadas
		imprimir("Erro: A partida não pode ser determinada em mais de 60 rounds. Provavel BUG.");
		throw new LamaException(-1, null, "Erro desconhecido. Mais de 60 turnos sem termino do jogo.", 0);
	}

	/* processarJogada retorna 1 se o jogador tiver se matado com o poder heroico */
	protected void processarJogada(Jogada umaJogada) throws LamaException {

		/* Pegando o valor da vida do meu heroi e da vida do heroi imigo*/
		int vidaEnemy = (jogador == 1? vidaHeroi2: vidaHeroi1);
		int minhaVida = (jogador == 1? vidaHeroi1: vidaHeroi2);

	    int usadoID;
		int alvoID;
		String errMessage;

		/* O ID -1 foi guardado para identificar o heroi e evitar o acesso a enderecos nulos */
	    if(umaJogada.getCartaJogada() == null) {
	    	usadoID = -1;
		} else {
			usadoID = umaJogada.getCartaJogada().getID();
		}

		switch(umaJogada.getTipo()) {

		case ATAQUE:

			/* Caso eu va atacar o heroi inimigo, atribuir ID = -1 para o id do alvo */
			/* Atualizando a carta alvo pelo ID */
			alvoID = setAlvoID(umaJogada);

			/* Atualizando a carta jogada pelo ID */
			if(umaJogada.getCartaJogada() == null) {
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

			umaJogada.setCartaJogada(
					lacaios.get(
							lacaios.indexOf(umaJogada.getCartaJogada())));

			/* Imprimindo a situacao da jogada e quem tentara ser atacado e por quem */
			if(umaJogada.getCartaJogada() instanceof CartaLacaio) {
				CartaLacaio l = (CartaLacaio) umaJogada.getCartaJogada();
                if(alvoID == -1) {
                        imprimir("JOGADA: O lacaio_id=" + usadoID + " atacou o heroi inimigo." +
                                " Vida do heroi inimigo: [" + vidaEnemy + " ---> "
                                        + (vidaEnemy - l.getAtaque()) + "].");
                }
				else {
                	if(umaJogada.getCartaAlvo() instanceof CartaLacaio) {
                		CartaLacaio al = (CartaLacaio) umaJogada.getCartaAlvo();
						imprimir("JOGADA: O lacaio_id=" + usadoID + " atacou o lacaio_id=" + alvoID + "." +
								" Vida do lacaio_id=" + alvoID + ": [" + al.getVidaAtual() + " ---> "
								+ (al.getVidaAtual() - l.getAtaque()) + "] - Vida do lacaio_id=" + l.getID()+ ": [" +
								l.getVidaAtual() + " ---> " +
								(l.getVidaAtual() - al.getAtaque()) + "].");

					}
				}
			}

			/* Procura por lacaios inimigos com o efeito de PROVOCAR */
			if(umaJogada.getCartaJogada() instanceof CartaLacaio) {
			   	if(((CartaLacaio) umaJogada.getCartaJogada()).getEfeito() != TipoEfeito.PROVOCAR) {
					if (provocar(alvoID)) {
						errMessage = "Erro: Existindo um lacaio com provocar, atacar outro alvo!\n";
						if(alvoID == -1) {
							if(jogador == 1) {
										errMessage += "ID do alvo invalido: Heroi 2\n";
							} else {
										errMessage += "ID do alvo invalido: Heroi 1\n";
							}
						} else {
									errMessage += "ID do alvo invalido: " + alvoID + "\n";
						}
						errMessage += "ID do lacaio com provocar: " + prov;
						imprimir(errMessage);
						throw new LamaException(13, umaJogada, errMessage, jogador == 1 ? 2 : 1);
					}
				}
			}

			if(ataqueDuploLigado) {
			    if(umaJogada.getCartaJogada() instanceof CartaLacaio) {
			    	CartaLacaio lac = (CartaLacaio)umaJogada.getCartaJogada();
					if(lac.getEfeito() == TipoEfeito.ATAQUE_DUPLO) {
						if(!ataqueDuploUsado.contains(lac.getID())) {
							if (lacaiosAtacaramID.contains(lac.getID())) {
								lacaiosAtacaramID.remove(lac.getID());
								ataqueDuploUsado.add(lac.getID());
							}
						} else {
							errMessage = "Erro: Atacar com um lacaio com o efeito de ataque duplo" +
									     " mais de duas vezes por turno!\n" +
                                         "ID atacante: " + usadoID;
                            imprimir(errMessage);
							throw new LamaException(7, umaJogada, errMessage, jogador == 1? 2 : 1);
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
                        }
                    }
                }
            }

            /* Caso eu tente atacar com um lacaio que acabou de ser baixado e ele nao possui
             * o efeito de INVESTIDA */
            if (lacaiosBaixados.contains(umaJogada.getCartaJogada().getID())){
                errMessage = "Erro: Atacar com um lacaio que foi baixado nesse turno\n!" +
                        " ID atacante: " + usadoID;
                imprimir(errMessage);
                throw new LamaException(6, umaJogada, errMessage, jogador == 1 ? 2 : 1);
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

						atualizaLacs(lacaios, jogada, alvo);
						atualizaLacs(lacaiosOponente, alvo, jogada);

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
								"ID Atacante invalido: " + usadoID + "\n" +
								"ID Alvo invalido: " + alvoID;
						imprimir(errMessage);
						throw new LamaException(8, umaJogada, errMessage, jogador == 1 ? 2 : 1);
					}
				}
				/* Caso a carta que vai atacar nao esteja entre os lacaios da minha mesa */
				else {
                    errMessage = "Erro: Atacar com um lacaio invalido de origem de ataque!\n" +
                            "ID Atacante invalido: " + usadoID;
                    imprimir(errMessage);
                    throw new LamaException(5, umaJogada, errMessage, jogador == 1 ? 2 : 1);
				}
			}

			/* Tratando o caso de um lacaio que tentar atacar novamente, verificando
			*  se o seu id ja esta numa tabela HASH */
			else {
				errMessage = "Erro: Atacar com um lacaio mais de uma vez por turno!\n" +
						"ID atacante: " + usadoID;
				imprimir(errMessage);
				throw new LamaException(7, umaJogada, errMessage, jogador == 1? 2 : 1);
			}
			break;
		case LACAIO:
            CartaLacaio lac;

            /* Recuperando o lacaio a ser baixado pelo id */
            if(umaJogada.getCartaJogada() != null) {
            	for(Carta a: mao) {
                    if(a.getID() == usadoID) {
                        umaJogada.setCartaJogada(a);
                    }
				}
			}

            if(lacaios.size() >= 7) {
            	errMessage = "Erro: Baixar um lacaio ja possuindo outros sete lacaios na mesa\n" +
            	"ID: " + usadoID + "\n" +
				"Nome da carta: " + umaJogada.getCartaJogada().getNome();
            	imprimir(errMessage);
            	throw new LamaException(4, umaJogada, errMessage, jogador==1? 2: 1);
			}
			if(umaJogada.getCartaJogada() instanceof CartaLacaio) {
				lac = (CartaLacaio)umaJogada.getCartaJogada();
                /* Imprimindo a tentativa de jogada */
				imprimir("JOGADA: O lacaio_id="+usadoID+" foi baixado para a mesa." +
						" Nome: [" +lac.getNome() + "] -" +
						" Vida: [" + lac.getVidaAtual() +"/" + lac.getVidaMaxima()+ "] -" +
						" Ataque: [" + lac.getAtaque() + "] -" +
						" Custo de Mana: [" + lac.getMana() +"] -" +
						" Efeito: [" + lac.getEfeito() + "].");
			} else {
				errMessage = "Erro: Tentar baixar na mesa uma carta de magia como carta lacaio\n" +
				"ID: " + usadoID + "\n" +
				"Nome da carta: " + umaJogada.getCartaJogada().getNome();
				imprimir(errMessage);
				throw new LamaException(3, umaJogada, errMessage, jogador==1? 2:1);
			}

			if(jogador == 1) {
				if (umaJogada.getCartaJogada().getMana() > manaJogador1) {
				    errMessage = "Erro: Realizar uma jogada que o limite de mana nao permite!\n" +
							"Tipo da jogada: baixar um lacaio na mesa" + "\n" +
							"Custo de mana: " + umaJogada.getCartaJogada().getMana() + "\n" +
							"Mana disponivel: " + manaJogador1;
			    	imprimir(errMessage);
			    	throw new LamaException(2, umaJogada, errMessage, 2);
				}
			} else {
				if (umaJogada.getCartaJogada().getMana() > manaJogador2) {
				    errMessage = "Erro: Realizar uma jogada que o limite de mana nao permite!\n" +
							"Tipo da jogada: baixar um lacaio na mesa" + "\n" +
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
				errMessage = "Erro: Tentou-se usar a carta_id=" + usadoID +
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
            if(!mao.contains(umaJogada.getCartaJogada())) {
					errMessage = "Erro: Tentou-se usar a carta_id=" + usadoID + "," +
							" porém esta carta não existe na mao. IDs de cartas na mao: ";
					for(Carta card : mao){
						errMessage += card.getID() + ", ";
					}
					imprimir(errMessage);
					// Dispara uma LamaException passando o código do erro, uma mensagem
					// descritiva correspondente e qual jogador deve vencer a partida.
					throw new LamaException(1, umaJogada, errMessage, jogador==1?2:1);
            }

		    if (umaJogada.getCartaJogada() instanceof CartaLacaio) {
				errMessage = "Erro: Tentar usar uma carta Lacaio como uma magia!\n" +
						"ID da carta usada: " + umaJogada.getCartaJogada().getID() + "\n" +
						"Nome da carta: " + umaJogada.getCartaJogada().getNome();
				imprimir(errMessage);
				throw new LamaException(9, umaJogada, errMessage, jogador == 1 ? 2 : 1);
			}

			/* Caso eu va atacar o heroi inimigo, atribuir ID = -1 para o id do alvo */
			alvoID = setAlvoID(umaJogada);

			/* Atualizando a carta jogada */
			for(Carta m : mao) {
                if(m.getID() == usadoID) {
                    umaJogada.setCartaJogada(m);
                }
            }

            /* Caso especial de atualizar o alvo, no caso em que a magia eh de BUFF */
            if(((CartaMagia) umaJogada.getCartaJogada()).getMagiaTipo() == TipoMagia.BUFF) {
                if(umaJogada.getCartaAlvo() instanceof CartaLacaio) {
                    for (Carta a : lacaios) {
                        if (a.getID() == umaJogada.getCartaAlvo().getID()) {
                            umaJogada.setCartaAlvo(a);
                        }
                    }
                }
            }

			/* O erro correspondente ao else do if acima nao eh tratado pelo enunciado */
			/* Quando uma magia eh invalida (null) */
			if(umaJogada.getCartaJogada() instanceof CartaMagia) {
				CartaMagia mag = (CartaMagia)umaJogada.getCartaJogada();

				/* Impressao da jogada de ataque de magia */
				if(mag.getMagiaTipo() == TipoMagia.ALVO) {
					if (umaJogada.getCartaAlvo() == null) {
						imprimir("JOGADA: A magia_id=" + usadoID + " atacou o alvo heroi inimigo." +
								" Vida do heroi inimigo: [" + vidaEnemy + " ---> "
								+ (vidaEnemy - mag.getMagiaDano())
								+ "].");
					} else {
						if (umaJogada.getCartaAlvo() instanceof CartaLacaio) {
							CartaLacaio alvo = (CartaLacaio) umaJogada.getCartaAlvo();
							imprimir("JOGADA: A magia_id=" + usadoID + " atacou o alvo lacaio_id="
									+ alvo.getID() + "." +
									" Vida do lacaio_id=" + alvoID + ": [" + alvo.getVidaAtual() + " ---> "
									+ (alvo.getVidaAtual() - mag.getMagiaDano())+ "].");
						}
					}
				} else if (mag.getMagiaTipo() == TipoMagia.AREA) {
					imprimir("JOGADA: A magia_id=" +usadoID +" de area atacou os lacaios e o heroi inimigo.");
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
						imprimir("JOGADA: a magia_id=" + usadoID + " buffou o lacaio_id=" + l.getID()+ "." +
								" Vida do lacaio_id=" + l.getID() + ": [" + l.getVidaAtual() + " ---> " +
								(l.getVidaAtual() + mag.getMagiaDano()) + "] - " +
								"Ataque do lacaio_id=" + l.getID() + ": [" + l.getAtaque() + " ---> " +
								(l.getAtaque() + mag.getMagiaDano()) + "].");
					}
				}
			}

			if(jogador == 1) {
				mana = manaJogador1;
				vidaHeroiInimigo = vidaHeroi2;
			} else {
				mana = manaJogador2;
				vidaHeroiInimigo = vidaHeroi1;
			}

            if(mana >= umaJogada.getCartaJogada().getMana()) {
                CartaMagia magia = (CartaMagia) umaJogada.getCartaJogada();
                Carta alvo = umaJogada.getCartaAlvo();
                mana -= magia.getMana();

                /* Se a magia de ataque for de area */
                if (magia.getMagiaTipo() == TipoMagia.AREA) {
                    mao.remove(magia);
                    /* Diminui a vida do heroi inimigo */
                    vidaHeroiInimigo -= magia.getMagiaDano();

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
                    for(CartaLacaio k : index) {
                        lacaiosOponente.remove(k);
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
                                errMessage = "Erro: Usar uma magia de buff em um alvo invalido!\n" +
                                        "ID do buff: " + magia.getID() + "\n" +
                                        "ID invalido do alvo: Heroi ";
                                errMessage += jogador==1? "2":"1";
                        } else {
                            errMessage = "Erro: Usar uma magia de buff em um alvo invalido!\n" +
                                "ID do buff: " + magia.getID() + "\n" +
                                "ID invalido do alvo: " + alvoID;
                        }
                        imprimir(errMessage);
                        throw new LamaException(10, umaJogada, errMessage, jogador==1?2:1);
                    }
                } else if (magia.getMagiaTipo() == TipoMagia.ALVO) {
                    mao.remove(magia);
                    if (alvo == null) {
                        vidaHeroiInimigo -= magia.getMagiaDano();
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
                                i--;
                            }
                        }
                    } else {
                        if(alvoID == -1) {
                                errMessage = "Erro: Usar uma magia de alvo em um alvo invalido!\n" +
                                        "ID do buff: " + magia.getID() + "\n" +
                                        "ID invalido do alvo: Heroi ";
                                errMessage += jogador==1? "2":"1";
                        } else {
                            errMessage = "Erro: Usar uma magia de alvo em um alvo invalido!\n" +
                                "ID do buff: " + magia.getID() + "\n" +
                                "ID invalido do alvo: " + alvoID;
                        }
                        imprimir(errMessage);
                        throw new LamaException(10, umaJogada, errMessage, jogador==1?2:1);
                    }
                }
            } else {
                errMessage = "Erro: Realizar uma jogada que o limite de mana nao permite!\n" +
                        "Tipo da jogada: " + umaJogada.getTipo() + "\n" +
                        "Custo de mana: " + umaJogada.getCartaJogada().getMana() + "\n" +
                        "Mana disponivel: " + mana;
                imprimir(errMessage);
                throw new LamaException(2, umaJogada, errMessage, jogador==1?2:1);
            }

            if(jogador == 1) {
				vidaHeroi2 = vidaHeroiInimigo;
				manaJogador1 = mana;
			} else {
				vidaHeroi1 = vidaHeroiInimigo;
				manaJogador2 = mana;
			}

			break;
		case PODER:

			alvoID = setAlvoID(umaJogada);

			if(umaJogada.getCartaAlvo() == null) {
				imprimir("JOGADA: poder heroico foi usado no heroi inimigo." +
                        " Vida do heroi inimigo: [" + vidaEnemy + " ---> "
						+ (vidaEnemy - 1) + "].");
			} else {
				if(umaJogada.getCartaAlvo() instanceof CartaLacaio) {
				    CartaLacaio l = (CartaLacaio)umaJogada.getCartaAlvo();
					imprimir("JOGADA: poder heroico foi usado em lacaio_id=" + alvoID + "." +
							" Vida do lacaio_id=" + alvoID +": [" + l.getVidaAtual() +
							" ---> " + (l.getVidaAtual() -1) + "] - Vida do meu heroi: [" +
							(minhaVida) + " ---> " + (minhaVida -l.getAtaque()) + "].");
				}
			}

			if(provocar(alvoID)) {
				errMessage = "Erro: Existindo um lacaio com provocar, atacar outro alvo\n";
			   	if(alvoID == -1) {
			   		if(jogador == 1) {
                            errMessage += "ID do alvo invalido: Heroi 2\n";
					} else {
							errMessage += "ID do alvo invalido: Heroi 1\n";
					}
				} else {
							errMessage += "ID do alvo invalido: " + alvoID + "\n";
				}
				errMessage += "ID do lacaio com provocar: " + prov;
				imprimir(errMessage);
				throw new LamaException(13, umaJogada, errMessage, jogador == 1 ? 2 : 1);
			}

			if(poderHeroicoUsado) {
				errMessage = "Erro: Usar o poder heroico mais de uma vez por turno!\n";
				if(alvoID == -1) {
					if(jogador == 1) {
								errMessage += "ID do alvo: Heroi 2";
					} else {
								errMessage += "ID do alvo: Heroi 1";
					}
				} else {
							errMessage += "ID do alvo: " + alvoID;
				}
				imprimir(errMessage);
				throw new LamaException(11, umaJogada, errMessage, jogador==1?2:1);
			}

			int vidaMeuHeroi;
			if(jogador == 1) {
				mana = manaJogador1;
				vidaHeroiInimigo = vidaHeroi2;
				vidaMeuHeroi = vidaHeroi1;
			} else {
				mana = manaJogador2;
				vidaHeroiInimigo = vidaHeroi1;
				vidaMeuHeroi = vidaHeroi2;
			}
            if(mana >= 2) {
                if (umaJogada.getCartaAlvo() == null) {
                    /* Diminui a vida do heroi inimigo */
                    vidaHeroiInimigo -= 1;
                } else {
                    if (lacaiosOponente.contains(umaJogada.getCartaAlvo())) {
                        CartaLacaio alvo = (CartaLacaio) umaJogada.getCartaAlvo();

                        /* Diminui a vida do meu heroi */
                        vidaMeuHeroi -= alvo.getAtaque();

                        for(int i = 0; i < lacaiosOponente.size(); i++) {
                            CartaLacaio l = (CartaLacaio) lacaiosOponente.get(i);
                            if (l.getID() == alvo.getID()) {
                                /* Diminuindo a vida do alvo */
                                l.setVidaAtual(alvo.getVidaAtual() - 1);
                            }
                            /* Removendo caso o alvo tenha morrido */
                            if(l.getVidaAtual() <= 0) {
                                lacaiosOponente.remove(i);
                                i--;
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
            if(jogador == 1) {
				manaJogador1 = mana;
				vidaHeroi2 = vidaHeroiInimigo ;
				vidaHeroi1 = vidaMeuHeroi;
			} else {
				manaJogador2 = mana;
				vidaHeroi1 = vidaHeroiInimigo;
				vidaHeroi2 = vidaMeuHeroi;
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