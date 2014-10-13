import com.mongodb.BasicDBObject
import com.mongodb.MongoClient

def mongoClient = new MongoClient();
def collection = mongoClient.getDB("Cafelito").getCollection("CoffeeShop")
collection.drop()

def xmlSlurper = new XmlSlurper().parse(new File('resources/all-coffee-shops.xml'))

xmlSlurper.node.each { child ->
    Map coffeeShop = [openStreetMapId: child.@id.text(),
                      location       : [coordinates: [Double.valueOf(child.@lon.text()),
                                                      Double.valueOf(child.@lat.text())],
                                        type       : 'Point']]
    child.tag.each { theNode ->
        def fieldName = theNode.@k.text()
        if (isValidFieldName(fieldName)) {
            coffeeShop.put(fieldName, theNode.@v.text())
        }
    }
    if (coffeeShop.name != null) {
        println coffeeShop
        collection.insert(new BasicDBObject(coffeeShop))
    }
}

println "\nTotal imported: $collection.count"

collection.createIndex(new BasicDBObject('location', '2dsphere'))

private boolean isValidFieldName(fieldName) {
    !fieldName.contains('.') && fieldName != 'location'
}
