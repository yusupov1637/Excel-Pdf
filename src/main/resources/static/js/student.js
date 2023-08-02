function openDeleteButton(e) {
    document.getElementById("deleteStudentModalInput").value = e.getAttribute('data-id');
    var modalOpenButton = document.getElementById("studentDeleteModalButtonId");
    modalOpenButton.click();
}