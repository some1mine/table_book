package site.jaeu95.table_book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.jaeu95.table_book.domain.form.AddReviewForm;
import site.jaeu95.table_book.domain.model.Customer;
import site.jaeu95.table_book.domain.model.Reservation;
import site.jaeu95.table_book.domain.model.Review;
import site.jaeu95.table_book.domain.model.Store;
import site.jaeu95.table_book.domain.repository.CustomerRepository;
import site.jaeu95.table_book.domain.repository.ReviewRepository;
import site.jaeu95.table_book.domain.repository.StoreRepository;
import site.jaeu95.table_book.exception.CustomException;
import site.jaeu95.table_book.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final CustomerRepository customerRepository;

    public Review addReview(AddReviewForm form) {
        Store store = storeRepository.findById(form.getStoreId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));
        Customer customer = customerRepository.findById(form.getCustomerId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (customer.getReservations().stream().filter(Reservation::isCheckedOuted)
                .anyMatch(reservation -> reservation.getStore().equals(store))) {
            throw new CustomException(ErrorCode.NOT_USER_RESERVATION);
        }

        Review review = reviewRepository.save(Review.from(customer, store, form));
        store.getReviews().add(review);
        customer.getReviews().add(review);

        return review;
    }
}
