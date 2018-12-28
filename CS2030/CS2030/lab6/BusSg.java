import java.util.Optional;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletableFuture;

/**
 * A BusSg class encapsulate the data related to the bus services and
 * bus stops in Singapore, and supports queries to the data.
 */
class BusSg {

  /**
   * Given a bus stop and a name, find the bus services that serve between
   * the given stop and any bus stop with matching mame.
   * @param  stop The bus stop
   * @param  name The (partial) name of other bus stops.
   * @return The (optional) bus routes between the stops.
   */
  public static Optional<BusRoutes> findBusServicesBetween(BusStop stop, String name) {
    Map<BusService, Set<BusStop>> validServices = new HashMap<>();
    Map<BusService, CompletableFuture<Set<BusStop>>> futures = new HashMap<>();
    return CompletableFuture.supplyAsync(() -> stop.getBusServices()) 
        .thenApply(services -> {
          for (BusService service : services) {
            futures.put(service, CompletableFuture.supplyAsync(() -> service.findStopsWith(name)));
          }
          for (BusService service : services) {
            Set<BusStop> set = futures.get(service).join();
            if (!set.isEmpty()) {
              validServices.put(service, set);
            }
          }
          return validServices;
        }).handle((result, exception) -> {
          if (exception != null) {
            System.err.println(exception);
            return new HashMap<BusService, Set<BusStop>>();
          } else {
            return result;
          }
        }).thenApply(validSvc -> {
          return Optional.of(new BusRoutes(stop, name, validSvc));
        }).join();
  }
}
