montagem:
	arm-eabi-as hamming-template.s -g -o hamming-template.o
ligador: montagem
	arm-eabi-ld hamming-template.o -g -o hamming-template -Ttext=0x77802000 -Tdata=0x77803000
criarSD: ligador 
	mksd.sh --so /home/specg12-1/mc404/simulador/dummyos.elf --user hamming-template
boot: criarSD 
	arm-sim --rom=/home/specg12-1/mc404/simulador/dumboot.bin --sd=disk.img -g 
gdb: 
	arm-eabi-gdb hamming-template
output: criarSD 
	(arm-sim --rom=/home/specg12-1/mc404/simulador/dumboot.bin --sd=disk.img 2>&1) > ra172358.out 
clean:
	rm hamming-template.o disk.img hamming-template
