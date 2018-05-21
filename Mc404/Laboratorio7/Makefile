montagem:
	arm-eabi-as ra172358.s -g -o ra172358.o
ligador: montagem
	arm-eabi-ld ra172358.o -g -o ra172358 -Ttext=0x77802000 -Tdata=0x77803000
criarSD: ligador 
	mksd.sh --so /home/specg12-1/mc404/simulador/dummyos.elf --user ra172358
boot: criarSD 
	arm-sim --rom=/home/specg12-1/mc404/simulador/dumboot.bin --sd=disk.img -g 
gdb: 
	arm-eabi-gdb ra172358
output: criarSD 
	(arm-sim --rom=/home/specg12-1/mc404/simulador/dumboot.bin --sd=disk.img 2>&1) > ra172358.out 
clean:
	rm ra172358.o disk.img ra172358
