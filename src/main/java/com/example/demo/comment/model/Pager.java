package com.example.demo.comment.model;

public class Pager {

	private Long page; // ���� �Խ��� ������
	private Long perPage; // �������� ���� ����
	private Long totalPage; // ��ü �������� ����
	private Long startRow; // ���� �������� ���� �۹�ȣ
	private Long lastRow; // ���� �������� ������ �۹�ȣ
	private Long block; // ���� pagionation
	private Long perBlock; // pagination�� page����
	private Long startNum; // pagination�� ���� ��������ȣ
	private Long lastNum; // pagination�� ������ ��������ȣ
	private boolean pre; // ����������
	private boolean next; // ����������
	
	private int product_id;

	public Pager() {
		this.page = 1L;
		this.perPage = 5L;
		this.perBlock = 10L;
	}

	public void setRow() {
		// ���������� 10���� ��� ����
		// page startrow lastrow
		// 1 1 10
		// 2 11 20
		// 3 21 30
		this.startRow = (this.getPage() - 1) * this.getPerPage();
		this.lastRow = this.getPage() * this.getPerPage();
	}

	public void setNum(Long totalCount) {
		// �Խ��� ���� �� ������ ��ü�������� ���� ����
		this.totalPage = totalCount % this.getPerPage() == 0 ? totalCount / this.getPerPage()
				: totalCount / this.getPerPage() + 1;
		// ��ü ���������� ��ü����� ���� ����
		Long totalBlock = totalPage % this.getPerBlock() == 0 ? totalPage / this.getPerBlock()
				: totalPage / this.getPerBlock() + 1;

		/*
		 * ������������ �ش��ϴ� �������� ���� page curBlcok 1 1 2 1 3 1 4 1 5 1 6 2
		 */
		Long curBlock = this.getPage() % this.getPerBlock() == 0 ? this.getPage() / this.getPerBlock()
				: this.getPage() / this.getPerBlock() + 1;

		/*
		 * �������� startNum, lastNum�� ���� curBlock startNum lastNum 1 1 5 2 6 10 3 11 16
		 */
		this.startNum = (curBlock - 1) * this.getPerBlock() + 1;
		this.lastNum = curBlock * this.getPerBlock();

		// �������� ����������̸� lastNum�� ��������������ȣ��
		if (curBlock == totalBlock) {
			this.lastNum = totalPage;
		}
		// 2�� �������̻� ���� ������ư Ȱ��ȭ
		if (this.page > 1) {
			pre = true;
		} else {
			pre = false;
		}
		// �������� ��������Ϻ��� ������ ������ư Ȱ��ȭ
		if (curBlock < totalBlock) {
			next = true;
		} else {
			next = false;
		}

	}

	public Long getPerPage() {
		if (this.perPage == null) {
			this.perPage = 10L;
		}
		return perPage;
	}

	// �������� null�̰ų� �����̸� 1�� �ʱ�ȭ (����ڰ� ���Ƿ� �������� �̻��� ���� �Է��ؼ� �̵��� ����)
	public Long getPage() {
		if (this.page == null || this.page <= 0) {
			this.page = 1L;
		}
		return page;
	}

	public Long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}

	public Long getStartRow() {
		return startRow;
	}

	public void setStartRow(Long startRow) {
		this.startRow = startRow;
	}

	public Long getLastRow() {
		return lastRow;
	}

	public void setLastRow(Long lastRow) {
		this.lastRow = lastRow;
	}

	public Long getBlock() {
		return block;
	}

	public void setBlock(Long block) {
		this.block = block;
	}

	public Long getPerBlock() {
		return perBlock;
	}

	public void setPerBlock(Long perBlock) {
		this.perBlock = perBlock;
	}

	public Long getStartNum() {
		return startNum;
	}

	public void setStartNum(Long startNum) {
		this.startNum = startNum;
	}

	public Long getLastNum() {
		return lastNum;
	}

	public void setLastNum(Long lastNum) {
		this.lastNum = lastNum;
	}

	public boolean isPre() {
		return pre;
	}

	public void setPre(boolean pre) {
		this.pre = pre;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public void setPerPage(Long perPage) {
		this.perPage = perPage;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	@Override
	public String toString() {
		return "Pager [page=" + page + ", perPage=" + perPage + ", totalPage=" + totalPage + ", startRow=" + startRow
				+ ", lastRow=" + lastRow + ", block=" + block + ", perBlock=" + perBlock + ", startNum=" + startNum
				+ ", lastNum=" + lastNum + ", pre=" + pre + ", next=" + next + ", product_id=" + product_id + "]";
	}

}
