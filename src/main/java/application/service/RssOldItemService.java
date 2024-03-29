package application.service;

import application.domain.RssFeed;
import application.domain.RssOldItem;
import application.repository.RssOldItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RssOldItemService {
    private RssOldItemRepository rssOldItemRepository;
    private static Logger LOGGER = LoggerFactory.getLogger(RssOldItemService.class);

    public RssOldItemService(RssOldItemRepository rssOldItemRepository) {
        this.rssOldItemRepository = rssOldItemRepository;
    }

    public RssOldItem addRecord(RssOldItem rssOldItem) {
        LOGGER.info("Dodanie rekordu: " + rssOldItem);
        return rssOldItemRepository.save(rssOldItem);
    }

    public boolean isRssItemExists(String title, String url, LocalDateTime dateTime, RssFeed rssFeed) {
        return rssOldItemRepository.findByTitleAndUrlAndAndDateTimeAndRssFeed(title, url, dateTime, rssFeed).isPresent();
    }

    public void removeRecordsByRssFeed(RssFeed rssFeed) {
        List<RssOldItem> rssOldItemsToDelete = rssOldItemRepository.findAllByRssFeed(rssFeed);
        rssOldItemRepository.deleteAll(rssOldItemsToDelete);
    }

    public List<RssOldItem> getAll() {
        return rssOldItemRepository.findAll();
    }

    public boolean isAtLeastRssItemNotSent() {
        for (RssOldItem rssOldItem : rssOldItemRepository.findAll()) {
            if (!rssOldItem.isSent()) {
                return true;
            }
        }
        return false;
    }

    public void changeStatusToSent() {
        rssOldItemRepository.findAll().stream()
                .filter(rssOldItem -> !rssOldItem.isSent())
                .forEach(rssOldItem -> {
                    rssOldItem.setSent(true);
                    rssOldItemRepository.save(rssOldItem);
                });
    }
}
