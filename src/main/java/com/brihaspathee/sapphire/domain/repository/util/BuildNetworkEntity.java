package com.brihaspathee.sapphire.domain.repository.util;

import com.brihaspathee.sapphire.domain.entity.LineOfBusiness;
import com.brihaspathee.sapphire.domain.entity.Network;
import com.brihaspathee.sapphire.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Node;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 15, December 2025
 * Time: 05:15
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.util
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
public class BuildNetworkEntity {

    /**
     * Constructs a Network object based on the provided Value representation.
     * The method extracts the necessary properties such as associated products
     * and lines of business, and uses them to populate a Network instance.
     *
     * @param netInfo the Value object containing the data for constructing the
     *                Network object, which includes network details, products,
     *                and lines of business. Must not be null.
     * @return a Network object built with the information extracted from the given
     *         Value, fully populated with its associated products and lines of
     *         business.
     */
    public static Network buildNetwork(Value netInfo){
        Node netNode = netInfo.get("net").asNode();
        Network network = buildNetwork(netNode);
        Value netDetails = netInfo.get("netDetails");
        List<Node> productNodes = netDetails.get("products").asList(Value::asNode);
        List<Product> products = productNodes.stream().map(BuildNetworkEntity::buildProduct).toList();
        network.setPartOfProducts(products);
        List<Node> lobNodes = netDetails.get("lobs").asList(Value::asNode);
        List<LineOfBusiness> lobs = lobNodes.stream().map(BuildNetworkEntity::buildLineOfBusiness).toList();
        network.setPartOfLinesOfBusiness(lobs);
        return network;
    }

    /**
     * Constructs a Network object based on the provided Node representation.
     * The method extracts the necessary properties from the given Node and uses them
     * to build a Network instance. It also sets additional attributes if present in the Node.
     *
     * @param networkNode the node containing the data for constructing the Network object.
     *                    If the node is null, this method will return null.
     * @return a Network object built from the data in the provided node, or null if
     *         the node is null.
     */
    public static Network buildNetwork(Node networkNode) {
        if (networkNode == null) {
            return null;
        }
        Network network = Network.builder()
                .elementId(networkNode.elementId())
                .code(networkNode.get("code").asString())
                .name(networkNode.get("name").asString())
                .build();
        log.debug("Network is HNET Network: {}", networkNode.get("isHNETNetwork"));
        log.debug("Network is Vendor Network: {}", networkNode.get("isVendorNetwork"));
        if (networkNode.containsKey("isHNETNetwork")) {
            network.setIsHNETNetwork(networkNode.get("isHNETNetwork").asBoolean());
        }
        if (networkNode.containsKey("isVendorNetwork")) {
            network.setIsVendorNetwork(networkNode.get("isVendorNetwork").asBoolean());
        }
        return network;
    }

    /**
     * Constructs a Product object based on the provided Node representation.
     * The method extracts the necessary properties from the given Node and uses them
     * to build a Product instance.
     *
     * @param productNode the node containing the data for constructing the Product object.
     *                    If the node is null, this method will return null.
     * @return a Product object built from the data in the provided node, or null if
     *         the node is null.
     */
    public static Product buildProduct(Node productNode){
        if (productNode == null) {
            return null;
        }
        Product product = Product.builder()
                .elementId(productNode.elementId())
                .code(productNode.get("code").asString())
                .name(productNode.get("name").asString())
                .build();
        return product;
    }

    /**
     * Constructs a LineOfBusiness object based on the provided Node representation.
     * The method extracts the required properties from the given Node and uses them
     * to build a LineOfBusiness instance.
     *
     * @param lobNode the node containing the data for constructing the LineOfBusiness object.
     *                If the node is null, this method will return null.
     * @return a LineOfBusiness object built from the data in the provided node, or null if
     *         the node is null.
     */
    public static LineOfBusiness buildLineOfBusiness(Node lobNode){
        if (lobNode == null) {
            return null;
        }
        LineOfBusiness lob = LineOfBusiness.builder()
                .elementId(lobNode.elementId())
                .code(lobNode.get("code").asString())
                .name(lobNode.get("name").asString())
                .build();
        return lob;
    }
}
