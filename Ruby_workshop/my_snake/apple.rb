class Apple
    attr_accessor :position

    def generate_position
       self.position = {:x => rand($var), :y => rand($var)} 
    end
    def initialize
        self.generate_position
    end
end
