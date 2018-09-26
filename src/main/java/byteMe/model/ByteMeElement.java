package byteMe.model;

public class ByteMeElement {

    private int roomID;
    private int elementID;
    private String elementContent;
    private String elementType;

    public ByteMeElement(int roomID, int elementID, String elementContent, String elementType) {
        this.roomID = roomID;
        this.elementID = elementID;
        this.elementContent = elementContent;
        this.elementType = elementType;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getElementID() {
        return elementID;
    }

    public void setElementID(int elementID) {
        this.elementID = elementID;
    }

    public String getElementContent() {
        return elementContent;
    }

    public void setElementContent(String elementContent) {
        this.elementContent = elementContent;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }
}
