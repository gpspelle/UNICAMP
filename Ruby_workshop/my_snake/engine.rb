load "constants.rb"
load "input.rb"
load "screen.rb"
load "snake.rb"
load "apple.rb"
load "event.rb"

class Engine

    def run
        screen = Screen.new
        snake = Snake.new
        apple = Apple.new
        event = Event.new
        score = 0
        while true
            movement = read_movement
            snake.move movement 
            break if movement == "quit"

            if event.snakeAteApple? snake.position, apple.position
                score += 1
                apple.generate_position
                snake.grow
            end
            if event.snakeHitWall? snake.position
                puts "Score: #{score}"
                break;
            end

            if event.snakeAteItself? snake.position
                puts "Score: #{score}"
                break;
            end
            puts "Running #{movement}"
            screen.draw snake.position, apple.position
        end
    end
end

engine = Engine.new
engine.run
