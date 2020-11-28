class Table {
    constructor(id, data, getHtmlFromRow, paginationId, dataPerPage) {
        this.id = id;
        this.data = data;
        this.getHtmlFromRow = getHtmlFromRow;
        this.pagination = new Pagination(this, paginationId, data.length, dataPerPage);
    }

    reload() {
        const table = document.querySelector(`#${this.id}`);
        while (table.firstChild) {
            table.removeChild(table.lastChild);
        }
        const startIndex = this.pagination.getStartIndex();
        for (let i = startIndex; i < Math.min(this.data.length, startIndex + this.pagination.dataPerPage); i++) {
            const row = this.data[i];
            table.insertAdjacentHTML("beforeend", this.getHtmlFromRow(i, row));
        }
    }
}

class Pagination {
    constructor(table, id, dataCount, dataPerPage) {
        this.table = table;
        this.id = id;
        this.currentPage = 1;
        this.dataCount = dataCount;
        this.dataPerPage = dataPerPage;
        this.totalPage = Math.ceil(dataCount / dataPerPage);

        const pagination = document.querySelector(`#${id}`);
        pagination.innerHTML = `
        <li class="disabled" id="${id}PrePage"><a href="#"><i class="material-icons">chevron_left</i></a></li>
        <span class="hide" id="${id}PreMore">...</span>
            ${
            Array.from({length: this.totalPage}, (_, i) => i + 1)
                .map(i => `<li class="waves-effect" id="${id}${i}"><a href="#">${i}</a></li>`)
                .join("\n")
        }
        <span class="hide" id="${id}NextMore">...</span>
        <li class="waves-effect" id="${id}NextPage"><a href="#"><i class="material-icons">chevron_right</i></a></li>
        `;
        if (this.totalPage > 3) {
            document.querySelector(`#${id}NextMore`).className = "";
        }
        pagination.children[2].className = "active";
        for (let i = 0; i < pagination.childElementCount; i++) {
            const element = pagination.children.item(i);
            if (i === 0) {
                element.onclick = e => {
                    e.preventDefault();
                    this.prePage();
                }
            } else if (i === pagination.childElementCount - 1) {
                element.onclick = e => {
                    e.preventDefault();
                    this.nextPage();
                }
                if (this.totalPage === 1) {
                    element.className = "disabled";
                }
            } else if (i > 1 && i < pagination.childElementCount - 2) {
                element.onclick = e => {
                    e.preventDefault();
                    this.onPageClick(i - 1);
                };
                if (i >= 5) {
                    element.classList.add("hide");
                }
            }
        }
    }

    prePage() {
        if (this.currentPage !== 1) {
            this.onPageClick(this.currentPage - 1);
        }
    }

    onPageClick(page) {
        document.querySelector(`#${this.id}${this.currentPage}`).className = "waves-effect";

        this.hidden(this.currentPage - 2);
        this.hidden(this.currentPage - 1);
        this.hidden(this.currentPage);
        this.hidden(this.currentPage + 1);
        this.hidden(this.currentPage + 2);

        this.removeHidden(page - 2);
        this.removeHidden(page - 1);
        document.querySelector(`#${this.id}${page}`).className = "active";
        this.removeHidden(page + 1);
        this.removeHidden(page + 2);

        document.querySelector(`#${this.id}PrePage`).className = page === 1 ? "disabled" : "waves-effect";
        document.querySelector(`#${this.id}NextPage`).className = page === this.totalPage ? "disabled" : "waves-effect";

        if (page - 3 > 0) {
            document.querySelector(`#${this.id}PreMore`).className = "";
        } else {
            document.querySelector(`#${this.id}PreMore`).className = "hide";
        }

        if (page + 2 < this.totalPage) {
            document.querySelector(`#${this.id}NextMore`).className = "";
        } else {
            document.querySelector(`#${this.id}NextMore`).className = "hide";
        }

        this.currentPage = page;
        this.table.reload();
    }

    removeHidden(page) {
        const pageElement = document.querySelector(`#${this.id}${page}`);
        if (pageElement) {
            pageElement.classList.remove("hide");
        }
    }

    hidden(page) {
        const pageElement = document.querySelector(`#${this.id}${page}`);
        if (pageElement) {
            pageElement.classList.add("hide");
        }
    }

    nextPage() {
        if (this.currentPage !== this.totalPage) {
            this.onPageClick(this.currentPage + 1);
        }
    }

    getStartIndex() {
        return (this.currentPage - 1) * this.dataPerPage;
    }
}
