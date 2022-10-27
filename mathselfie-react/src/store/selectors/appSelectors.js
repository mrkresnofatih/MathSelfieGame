import { useSelector } from "react-redux";

export const useAppPageSelector = () => useSelector((state) => state.app.page);