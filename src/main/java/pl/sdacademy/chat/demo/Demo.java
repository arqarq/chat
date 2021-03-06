package pl.sdacademy.chat.demo;

class Demo {
    public static void main(String[] args) {
        Location loc = new Location(10, 20, 15);

        int cc = 10;
        loc.move(cc, 10, 5);
        System.out.println(cc);
    }
}

class Location extends Point {
    private Integer z;

    Location(Integer xc, Integer yc, Integer zc) {
        super(xc, yc);
        z = zc;
    }

    void move(Integer dx, Integer dy, Integer dz) {
        dx++;
        x = x + dx;
        y = y + dy;
        z = z + dz;

        System.out.println("Point x location: " + x);
        System.out.println("Point y location: " + y);
        System.out.println("Point z location: " + z);
    }
}

class Point {
    Integer x;
    Integer y;

    Point(Integer xc, Integer yc) {
        x = xc;
        y = yc;
    }

    private void move(Integer dx, Integer dy) {
        x = x + dx;
        y = y + dy;

        System.out.println("Point x location: " + x);
        System.out.println("Point y location: " + y);
    }

    class Pointy {
        void cos() {
            move(2, 2);
        }
    }
}
