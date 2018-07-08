class Event
    def snakeAteApple? snake_position, apple_position
        snake_position.any? do |pos|
            pos[:x] == apple_position[:x] && pos[:y] == apple_position[:y]
        end
    end
    def snakeHitWall? snake_position
        snake_position.any? do |pos|
            (pos[:x] > ($var -1) || pos[:x] < 0) || (pos[:y] > ($var -1) || pos[:y] <0)
        end
    end
    
    def snakeAteItself? snake_position
        snake_position.uniq{|p| p.values_at(:x, :y)}.size < snake_position.size
    end
end
