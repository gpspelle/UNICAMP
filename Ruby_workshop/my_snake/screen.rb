class Screen
    def is_snake? (snake_position, x, y)
        snake_position.any? do |pos|
            pos[:x] == x && pos[:y] == y
        end
    end
    
    def is_apple? (apple_position, x, y)
        apple_position[:x] == x && apple_position[:y] == y
    end
    def draw (snake_position, apple_position)
        $var.times do |j|
            $var.times do |i|
                if is_snake? snake_position, i, j
                    print "[\e[32m0\e[0m]"
                elsif is_apple? apple_position, i, j
                    print "[\e[31mX\e[0m]"
                else
                    print "[ ]"
                end
            end
            print "\n"
        end

        for number in (1..3*$var)
            print "-"
        end
        puts ""
    end
end
