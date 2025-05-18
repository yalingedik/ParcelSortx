package data_structs;

import java.util.LinkedList;
import java.util.Queue;
import Parcel;

public class DestinationSorter {

    private class Node {
        String cityName;
        Queue<Parcel> parcelList;
        Node left, right;

        Node(String cityName) {
            this.cityName = cityName;
            this.parcelList = new LinkedList<>();
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public DestinationSorter() {
        root = null;
    }
asdas
    // 🟢 Parcel ekleme
    public void insertParcel(Parcel parcel) {
        root = insertParcelRecursive(root, parcel);
    }

    private Node insertParcelRecursive(Node node, Parcel parcel) {
        if (node == null) {
            Node newNode = new Node(parcel.destinationCity);
            newNode.parcelList.add(parcel);
            return newNode;
        }

        int compare = parcel.destinationCity.compareToIgnoreCase(node.cityName);
        if (compare < 0) {
            node.left = insertParcelRecursive(node.left, parcel);
        } else if (compare > 0) {
            node.right = insertParcelRecursive(node.right, parcel);
        } else {
            // Aynı şehirse kuyruğa ekle
            node.parcelList.add(parcel);
        }
        return node;
    }

    // 🟡 Belirli bir şehir için kuyruktaki tüm kargoları al
    public Queue<Parcel> getCityParcels(String city) {
        Node node = search(root, city);
        return (node != null) ? node.parcelList : null;
    }

    private Node search(Node node, String city) {
        if (node == null) return null;
        int compare = city.compareToIgnoreCase(node.cityName);
        if (compare < 0) return search(node.left, city);
        else if (compare > 0) return search(node.right, city);
        else return node;
    }

    // 🔴 Belirli şehirden bir parcel sil (kargo gönderildikten sonra)
    public boolean removeParcel(String city, String parcelID) {
        Node node = search(root, city);
        if (node != null && !node.parcelList.isEmpty()) {
            for (Parcel p : node.parcelList) {
                if (p.parcelID.equals(parcelID)) {
                    node.parcelList.remove(p);
                    return true;
                }
            }
        }
        return false;
    }

    // 🟢 Şehir adına göre alfabetik sıralı BST dolaşımı
    public void inOrderTraversal() {
        inOrderRecursive(root);
    }

    private void inOrderRecursive(Node node) {
        if (node != null) {
            inOrderRecursive(node.left);
            System.out.println("City: " + node.cityName + " | Parcel Count: " + node.parcelList.size());
            inOrderRecursive(node.right);
        }
    }

    // 🔍 Şehirde kaç kargo var?
    public int countCityParcels(String city) {
        Node node = search(root, city);
        return (node != null) ? node.parcelList.size() : 0;
    }

    // 🌳 BST yüksekliği
    public int getHeight() {
        return calculateHeight(root);
    }

    private int calculateHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
    }

    // 📊 Toplam şehir (düğüm) sayısı
    public int getCityCount() {
        return countNodes(root);
    }

    private int countNodes(Node node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    // 🚩 En çok yüke sahip şehir (en fazla parcel içeren node)
    public String getBusiestCity() {
        return findMaxCity(root, null, 0);
    }

    private String findMaxCity(Node node, String maxCity, int maxCount) {
        if (node == null) return maxCity;
        if (node.parcelList.size() > maxCount) {
            maxCity = node.cityName;
            maxCount = node.parcelList.size();
        }
        maxCity = findMaxCity(node.left, maxCity, maxCount);
        maxCity = findMaxCity(node.right, maxCity, maxCount);
        return maxCity;
    }
}
