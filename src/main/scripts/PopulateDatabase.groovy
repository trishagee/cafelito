import com.mongodb.BasicDBObject
import com.mongodb.MongoClient

def mongoClient = new MongoClient();
def collection = mongoClient.getDB("TrishaCoffee").getCollection("coffeeshop")
collection.drop()

def xmlSlurper = new XmlSlurper().parse(new File('resources/all-coffee-shops.xml'))

xmlSlurper.node.each { child ->
    Map coffeeShop = ['openStreetMapId': child.@id.text(),
                      'location'       : ['coordinates': [Double.valueOf(child.@lon.text()), Double.valueOf(child.@lat.text())],
                                          'type'       : 'Point']]
    child.tag.each { theNode ->
        coffeeShop.put(theNode.@k.text(), theNode.@v.text())
    }
    if (coffeeShop.name != null) {
        println coffeeShop
        collection.insert(new BasicDBObject(coffeeShop))
    }
}

println "\nTotal imported: $collection.count"

collection.createIndex(new BasicDBObject('location', '2dsphere'))

