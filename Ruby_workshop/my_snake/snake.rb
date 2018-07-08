class Snake
    attr_accessor :position
    attr_accessor :size_to_increase

    def grow 
        self.size_to_increase += 1
    end
    def move direction 
        new_position = {
            :x => self.position[0][:x],
            :y => self.position[0][:y]
        }
        
        case direction
            when "up"
                new_position[:y] -= 1
            when "down"
                new_position[:y] += 1
            when "left"
                new_position[:x] -= 1
            when "right"
                new_position[:x] += 1
        end
    
        self.position.unshift (new_position)
        if self.size_to_increase == 0
            self.position.pop
        else
            self.size_to_increase -= 1
        end
    end 
    def initialize
        @position = [{:x => ($var)/2, :y => ($var)/2}]
        self.size_to_increase = 0
    end
end
