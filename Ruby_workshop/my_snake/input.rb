require 'io/console'

def read_movement
	STDIN.echo = false
	STDIN.raw!

	input = STDIN.getc.chr
	if input == "\e" then
    input << STDIN.read_nonblock(3) rescue nil
    input << STDIN.read_nonblock(2) rescue nil
	end

	STDIN.echo = true
	STDIN.cooked!

	case input
	when "\e[A"
    return "up"
  when "\e[B"
    return "down"
  when "\e[C"
    return "right"
  when "\e[D"
    return "left"
  else 
    return "quit"
  end
end
