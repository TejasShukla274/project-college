package hotel.model;

public class Room {
    private int roomNumber;
    private String roomType;
    private boolean available;
    private double price;

    public Room(int roomNumber, String roomType, boolean available, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.available = available;
        this.price = price;
    }

    // Getters
    public int getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public boolean isAvailable() { return available; }
    public double getPrice() { return price; }

    // Setters
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    public void setAvailable(boolean available) { this.available = available; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return roomType + " - Room #" + roomNumber;
    }
}
